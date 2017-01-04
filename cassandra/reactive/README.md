# Spring Data Cassandra 2.0 - Reactive examples

This project contains samples of reactive data access features with Spring Data (Cassandra).

## Reactive Template API usage with `ReactiveCassandraTemplate`

The main reactive Template API class is `ReactiveCassandraTemplate`, ideally used through its interface `ReactiveCassandraOperations`. It defines a basic set of reactive data access operations using [Project Reactor](http://projectreactor.io) `Mono` and `Flux` reactive types.

```java
template.insert(Flux.just(new Person("Walter", "White", 50),
				new Person("Skyler", "White", 45),
				new Person("Saul", "Goodman", 42),
				new Person("Jesse", "Pinkman", 27)));

Flux<Person> flux = template.select(select()
                                         .from("person")
                                         .where(eq("lastname", "White")), Person.class);
```

The test cases in `ReactiveCassandraTemplateIntegrationTest` show basic Template API usage.
Reactive data access reads and converts individual elements while processing the stream.


## Reactive Repository support

Spring Data Cassandra provides reactive repository support with Project Reactor and RxJava 1 reactive types. The reactive API supports reactive type conversion between reactive types.

```java
public interface ReactivePersonRepository extends ReactiveCrudRepository<Person, String> {

	Flux<Person> findByLastname(String lastname);

	@Query("SELECT * FROM person WHERE firstname = ?0 and lastname  = ?1")
	Mono<Person> findByFirstnameAndLastname(String firstname, String lastname);

	// Accept parameter inside a reactive type for deferred execution
	Flux<Person> findByLastname(Mono<String> lastname);

	Mono<Person> findByFirstnameAndLastname(Mono<String> firstname, String lastname);
}
```

```java
public interface RxJava1PersonRepository extends RxJava1CrudRepository<Person, String> {

	Observable<Person> findByLastname(String lastname);

	@Query("SELECT * FROM person WHERE firstname = ?0 and lastname  = ?1")
	Single<Person> findByFirstnameAndLastname(String firstname, String lastname);

	// Accept parameter inside a reactive type for deferred execution
	Observable<Person> findByLastname(Single<String> lastname);

	Single<Person> findByFirstnameAndLastname(Single<String> firstname, String lastname);
}
```
