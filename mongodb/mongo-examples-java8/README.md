# Spring Data MongoDB - Java 8 examples

This project contains samples of Java 8 specific features of Spring Data (MongoDB).

## Support for JDK 8's `Stream` for repository methods

```java
public interface PersonRepository extends CrudRepository<Person, String> {
	
	//Custom Query method returning a Java 8 Stream
	@Query("{}")
	Stream<Person> findAllByCustomQueryWithStream();
}
```