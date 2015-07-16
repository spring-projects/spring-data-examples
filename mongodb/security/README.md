# Spring Data MongoDB - Spring Security integration.

This project contains samples of the Spring Security integration in Spring Data (MongoDB).

## Support for SpEL Expression based filtering

```java
public interface PersonRepository extends CrudRepository<Person, String> {

  @Override
  List<Person> findAll();

  //Custom Query method with filtering based on Spring Security context information
  @Query("{id: ?#{ hasRole('ROLE_ADMIN') ? {$exists:true} : principal.id}}")
  List<Person> findAllForCurrentUserById();
}
```

