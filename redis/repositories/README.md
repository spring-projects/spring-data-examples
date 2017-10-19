# Spring Data Redis - Repository Examples #

This project contains examples for Spring Data specific repository abstraction on top of Redis.

## Repository Suport ##

Redis Repository support allows to convert, store, retrieve and index entities within Redis native data structures. To do, besides the `HASH` containing the actual properties several [Secondary Index](http://redis.io/topics/indexes) structures are set up and maintained.

```java
@RedisHash("persons")
class Person {

  @Id String id;

  @Indexed String firstname;
  @Indexed String lastname;

  Gender gender;
  Address address;

  @Reference List<Person> children;
}
```

The above entity would for example then be stored in a Redis [HASH](http://redis.io/topics/data-types#hashes) with key `persons:9b0ed8ee-14be-46ec-b5fa-79570aadb91d`.

```properties
_class=example.springdata.redis.domain.Person                <1>
id=9b0ed8ee-14be-46ec-b5fa-79570aadb91d
firstname=eddard                                             <2>
lastname=stark
gender=MALE
address.city=winterfell                                      <3>
address.country=the north
children.[0]=persons:41436096-aabe-42fa-bd5a-9a517fbf0260    <4>
children.[1]=persons:1973d8e7-fbd4-4f93-abab-a2e3a00b3f53
children.[2]=persons:440b24c6-ede2-495a-b765-2d8b8d6e3995
```
```
<1> The _class attribute is used to store the actual type and is required for object/hash conversion.
<2> Values are also included in Secondary Index when annotated with @Indexed.
<3> Complex types are flattened out and embedded into the HASH as long as there is no explicit Converter registered or a @Reference annotation present.
<4> Using @Reference stores only the key of a referenced object without embedding values like in <3>.
```

Additionally indexes are created for `firstname`, `lastname` and `address.city` containing the `id` of the actual entity.

```bash
redis/src $ ./redis-cli keys *
   1) "persons"                                             <1>
   2) "persons:9b0ed8ee-14be-46ec-b5fa-79570aadb91d"        <2>
   3) "persons:9b0ed8ee-14be-46ec-b5fa-79570aadb91d:idx"    <3>
   4) "persons:41436096-aabe-42fa-bd5a-9a517fbf0260"
   5) "persons:41436096-aabe-42fa-bd5a-9a517fbf0260:idx"
   6) "persons:1973d8e7-fbd4-4f93-abab-a2e3a00b3f53"
   7) "persons:1973d8e7-fbd4-4f93-abab-a2e3a00b3f53:idx"
   8) "persons:440b24c6-ede2-495a-b765-2d8b8d6e3995"
   9) "persons:440b24c6-ede2-495a-b765-2d8b8d6e3995:idx"
  10) "persons:firstname:eddard"                            <4>
  11) "persons:firstname:robb"
  12) "persons:firstname:sansa"
  13) "persons:firstname:arya"
  14) "persons:lastname:stark"                              <5>
  15) "persons:address.city:winterfell"                     <6>
```
```
<1> SET holding all ids known in the keyspace "persons".
<2> HASH holding property values for id "9b0ed8ee-14be-46ec-b5fa-79570aadb91d" in keyspace "persons".
<3> SET holding index information for CRUD operations.
<4> SET used for indexing entities with "firstname" equal to "eddard" within keyspace "persons".
<5> SET used for indexing entities with "lastname" equal to "stark"  within keyspace "persons".
<6> SET used for indexing entities with "address.city" equal to "winterfell"  within keyspace "persons".
```

## Configuration ##

The below configuration uses [Lettuce](https://github.com/lettuce-io/lettuce-core) to connect to Redis on its default port. Please note the usage of `@EnableRedisRepositories` to create `Repository` instances.

```java
@Configuration
@EnableRedisRepositories
class AppConfig {

  @Bean
  RedisConnectionFactory connectionFactory() {
    return new LettuceConnectionFactory();
  }

  @Bean
  RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {

    RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);

    return template;
  }
}
```

Having the infrastructure in place you can go on declaring and using the `Repository` interface.

```java
interface PersonRepository extends CrudRepository<Person, String> {

  List<Person> findByLastname(String lastname);

  Page<Person> findByLastname(String lastname, Pageable page);

  List<Person> findByFirstnameAndLastname(String firstname, String lastname);

  List<Person> findByFirstnameOrLastname(String firstname, String lastname);

  List<Person> findByAddress_City(String city);
}
```

## One Word of Caution ##

Maintaining complex types and index structures stands and falls with its usage. Please consider the following:

* Nested document structures increase object <> hash conversion overhead.
* Consider having only those indexes you really need instead of indexing each and every property.
* Pagination is a costly operation since the total number of elements is calculated before returning just a slice of data.
* Pagination gives no guarantee on information as elements might be added, moved or removed.
