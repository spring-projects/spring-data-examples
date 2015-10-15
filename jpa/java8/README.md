# Spring Data - Java 8 examples

This project contains samples of Java 8 specific features of Spring Data (JPA).

## Support for JDK 8's `Optional` for repository methods

```java
interface CustomerRepository extends Repository<Customer, Long> {

  // CRUD method using Optional
  Optional<Customer> findOne(Long id);

  // Query method using Optional
  Optional<Customer> findByLastname(String lastname);
}
```

* `CustomerRepository.findOne(Long)` effectively "overrides" the default `CrudRepository.findOne(…)` to return `Optional.empty()` instead of `null` in case no unique element satisfying the query can be found.
* `CustomerRepository.findByLastname(…)` does the same for a normal query method.

## Support for JDK 8's date/time types in the auditing sub-system

* `Customer` extends `AbstractEntity` which contains fields of JDK 8's new date/time API and those can be populated the same way legacy types like `Date` and `Calendar` can.

## Support for JDK 8' `Stream` in repository methods

JPA repositories can now use `Stream` as return type for query methods to trigger streamed execution of the query. This will cause Spring Data JPA to use persistence provider specific API to traverse the query result one-by-one.

```java
interface CustomerRepository extends Repository<Customer, Long> {

  @Query("select c from Customer c")
  Stream<Customer> streamAllCustomers();
}

try (Stream<Customer> customers = repository.streamAllCustomers()) {
  // use the stream here
}
```

Note how the returned `Stream` has to be used in a try-with-resources clause as the underlying resources have to be closed once we finished iterating over the result.


## Support for JDK 8' `CompletableFuture` in `@Async` repository methods

JPA repositories can now use `CompletableFuture` as return type for query methods for async execution of the query with support for fluent processing.

Note that: Support for `CompletableFuture` in combination with `@Async` is available in Spring Framework 4.2.x.

```java
interface CustomerRepository extends Repository<Customer, Long> {

  @Async
  CompletableFuture<List<Customer>> readAllBy();
}

CompletableFuture<List<Customer>> future = repository.readAllBy().thenApply(this::doSomethingWithCustomers);

while (!future.isDone()) {
	log.info("Waiting for the CompletableFuture to finish...");
	TimeUnit.MILLISECONDS.sleep(500);
}

List<Customer> processedCustomers = future.get();
```
