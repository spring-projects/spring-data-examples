# Spring Data Couchbase 3.0 - Reactive examples

This project contains samples of reactive data access features with Spring Data (Couchbase).

## Reactive Template API usage with `RxJavaCouchbaseOperations`

The main reactive Template API class is `RxJavaCouchbaseTemplate`, ideally used through its interface `RxJavaCouchbaseOperations`. It defines a basic set of reactive data access operations using [RxJava 1](https://github.com/ReactiveX/RxJava/tree/1.x) `Single` and `Observable` reactive types.

```java
Airline airline = new Airline();

Observable<Airline> single = operations.save(airline)

Observable<Airline> airlines = operations.findByView(ViewQuery.from("airlines", "all"), Airline.class);
```

The test cases in `RxJavaCouchbaseOperationsIntegrationTests` show basic Template API usage.
Reactive data access reads and converts individual elements while processing the stream.


## Reactive Repository support

Spring Data Couchbase provides reactive repository support with Project Reactor, RxJava 1 and RxJava 2 reactive types. The reactive API supports reactive type conversion between reactive types.

```java
@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "airlines")
public interface ReactiveAirlineRepository extends ReactiveCrudRepository<Airline, String> {

    Mono<Airline> findAirlineByIataCode(String code);

    @View(designDocument = "airlines", viewName = "all")
    Flux<Airline> findAllBy();
}
```

```java
@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "airlines")
public interface RxJava1AirlineRepository extends Repository<Airline, String> {

    Single<Airline> findAirlineByIataCode(String code);

    @View(designDocument = "airlines", viewName = "all")
    Observable<Airline> findAllBy();

    Single<Airline> findById(String id);

    Single<Airline> save(Airline airline);
}
```
