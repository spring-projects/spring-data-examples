# Spring Data Cassandra - Java 8 examples

This project contains samples of Java 8 specific features of Spring Data (Cassandra).

## Support for JDK 8's `Stream` for repository methods

Repository methods can use a Java 8 `Stream` as a return type which will cause the reading of the results and the to-object-conversion of rows to happen while iterating over the stream.

```java
public interface PersonRepository extends CrudRepository<Person, String> {

  @Override
  List<Person> findAll();

  // Derived query method returning a Java 8 Stream
  Stream<Person> findAll();
}
```

The test cases in `PersonRepositoryIntegrationTest` oppose a plain `List` based query method with one that uses a `Stream` and shows how the former pulls all data into memory first and the iteration is done over the pre-populated list. The execution of the `Stream`-based method in contrast shows that the individual elements are read and converted while iterating the stream.
