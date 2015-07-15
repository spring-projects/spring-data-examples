# Spring Data Web - Querydsl example

This example shows some of the Spring Data Querydsl integration features with Spring MVC.

## Quickstart

1. Install MongoDB (http://www.mongodb.org/downloads, unzip, run `mkdir data`, run `bin/mongod --dbpath=data`)
2. Build and run the app (`mvn spring-boot:run`)
4. Access app directly via its UI (`http://localhost:8080/`).

## Interesting bits

The most core piece of interest here is `UserController` and how it consumes Querydsl `Predicate` instances derived from the request:

```java
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
class UserController {

  private final UserRepository repository;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  String index(Model model,
      @QuerydslPredicate(root = User.class) Predicate predicate, Pageable pageable,
      @RequestParam MultiValueMap<String, String> parameters) {

    … = userRepository.findAll(predicate, pageable);

  }
}
```

As you can see `Predicate` can be used as Spring MVC controller argument. It will automatically populate such a `Predicate` with values from the current request based on the type configured in `@QuerydslPredicate`. Explicit bindings can be configured by either explictly defining one in the annotation, too. By default, we will inspect the domain types's repository for binding customizations. In this example, it looks like this:

```java
public interface UserRepository
    extends CrudRepository<User, String>, QueryDslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {

  @Override
  default public void customize(QuerydslBindings bindings, QUser root) {

    bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    bindings.excluding(root.password);
  }
}
```

The repository extends `QuerydslBinderCustomizer` which exposes a `QuerydslBindings` instance for customization. It allows for property based (by using Querydsl's meta-model types) and type based customizations of the value binding. The example here defines a `String`-properties to be bound using the `containsIgnoreCase(…)` operator. For further information checkout the JavaDoc of [QuerydslBindings](http://docs.spring.io/spring-data/commons/docs/1.11.0.RC1/api/org/springframework/data/querydsl/binding/QuerydslBindings.html).

## Technologies used

- Spring Data MongoDB, Spring MVC & Querydsl
- MongoDB
- Spring Batch (to read the CSV file containing the user data and pipe it into MongoDB)
- Spring Boot
