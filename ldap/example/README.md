# Spring Data LDAP - Example

This project contains samples of Spring Data (LDAP).


```java
public interface PersonRepository extends CrudRepository<Person, Name> {

  List<Person> findByUid(String uid);

  List<Person> findByLastnameStartsWith(String prefix);
}
```

The test cases in `PersonRepositoryIntegrationTests` show basic interaction to search, create and modify objects stored in a LDAP repository.
