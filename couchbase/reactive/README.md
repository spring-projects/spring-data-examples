# Spring Data Couchbase 4.0 - Reactive examples

This project contains samples of reactive data access features with Spring Data (Couchbase).


## Reactive Repository support

Spring Data Couchbase provides reactive repository support with Project Reactor:

```java
public interface ReactiveAirlineRepository extends ReactiveCrudRepository<Airline, String> {

    Mono<Airline> findByIata(String code);

    Flux<Airline> findAllBy();
}
```

For more information, see the [official documentation](https://docs.spring.io/spring-data/couchbase/docs/current/reference/html/#reference).

