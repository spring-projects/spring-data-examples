# Spring Data MongoDB 2.0 - Reactive examples

This project contains samples of reactive data access features with Spring Data (MongoDB).

## Prerequisites

MongoDB requires the Reactive Streams driver to provide reactive data access.
The Reactive Streams driver maintains its own connections. Using Spring Data MongoDB Reactive support
together with blocking Spring Data MongoDB data access will open multiple connections to your MongoDB servers.

## Reactive Template API usage with `ReactiveMongoTemplate`

The main reactive Template API class is `ReactiveMongoTemplate`, ideally used through its interface `ReactiveMongoOperations`. It defines a basic set of reactive data access operations using [Project Reactor](http://projectreactor.io) `Mono` and `Flux` reactive types.

```java
template.insertAll(Arrays.asList(new Person("Walter", "White", 50),
				new Person("Skyler", "White", 45),
				new Person("Saul", "Goodman", 42),
				new Person("Jesse", "Pinkman", 27)));

Flux<Person> flux = template.find(Query.query(Criteria.where("lastname").is("White")), Person.class);
```

The test cases in `ReactiveMongoTemplateIntegrationTest` show basic Template API usage.
Reactive data access reads and converts individual elements while processing the stream.


## Reactive Repository support

Spring Data MongoDB provides reactive repository support with Project Reactor and RxJava 2 reactive types. The reactive API supports reactive type conversion between reactive types.

```java
public interface ReactivePersonRepository extends ReactiveCrudRepository<Person, String> {

	Flux<Person> findByLastname(String lastname);

	@Query("{ 'firstname': ?0, 'lastname': ?1}")
	Mono<Person> findByFirstnameAndLastname(String firstname, String lastname);

	// Accept parameter inside a reactive type for deferred execution
	Flux<Person> findByLastname(Mono<String> lastname);

	Mono<Person> findByFirstnameAndLastname(Mono<String> firstname, String lastname);

	@Tailable // Use a tailable cursor
	Flux<Person> findWithTailableCursorBy();
}
```

```java
public interface RxJava2PersonRepository extends RxJava2CrudRepository<Person, String> {

	Flowable<Person> findByLastname(String lastname);

	@Query("{ 'firstname': ?0, 'lastname': ?1}")
	Maybe<Person> findByFirstnameAndLastname(String firstname, String lastname);

	// Accept parameter inside a reactive type for deferred execution
	Flowable<Person> findByLastname(Single<String> lastname);

	Maybe<Person> findByFirstnameAndLastname(Single<String> firstname, String lastname);

	@Tailable // Use a tailable cursor
	Flowable<Person> findWithTailableCursorBy();
}
```
