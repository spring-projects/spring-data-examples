# Spring Data MongoDB - Query-by-Example (QBE) example

This project contains samples of Query-by-Example of Spring Data MongoDB.

## Support for Query-by-Example

Query by Example (QBE) is a user-friendly querying technique with a simple interface. It
allows dynamic query creation and does not require to write queries containing field
names. In fact, Query by Example does not require to write queries using BSON at all.

An `Example` takes a data object (usually the entity object or a subtype of it) and a
specification how to match properties. You can use Query by Example with `MongoOperations`
and Repositories.

```java
interface PersonRepository extends CrudRepository<Person, String>, QueryByExampleExecutor<Person> {
}
```

```java
Example<Person> example = Example.of(new Person("Jon", "Snow"));
repo.findAll(example);


ExampleMatcher matcher = ExampleMatcher.matching().
    .withMatcher("firstname", endsWith())
    .withMatcher("lastname", startsWith().ignoreCase());

Example<Person> example = Example.of(new Person("Jon", "Snow"), matcher); 
repo.count(example);
```

This example contains two test classes to illustrate Query-by-Example with `MongoOperations` in `MongoOperationsIntegrationTests` and the usage with a Repository in `UserRepositoryIntegrationTests`.

