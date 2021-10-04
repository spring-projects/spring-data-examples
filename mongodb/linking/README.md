# Spring Data MongoDB - Linking Example

This project contains examples for linking Documents using:

* `@DBRef`
* `@DocumentReference`

## DBRef

Use MongoDB native `dbref` for storing associations.

```java
class Manager {
	
    @DBRef
    private List<Employee> employees;

    // ...
}
```

## DocumentReference

Link documents via their id.

```java
class Manager {
	
    @DocumentReference
    private List<Employee> employees;

    // ...
}
```

Link documents via a (non _id_) property.

```java
class Manager {
	
    @DocumentReference(lookup = "{ 'name' : ?#{#target} }")
    private List<Employee> employees;

    // ...
}
```

Link documents JPA style.

```java
class Manager {

    @ReadOnlyProperty
    @DocumentReference(lookup="{'managerId':?#{#self._id} }")
    private List<Employee> employees;

    // ...
}
```
----

For more information please refer to the [Reference Documentation](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mapping-usage-references).
