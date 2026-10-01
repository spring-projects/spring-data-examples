# Spring Data JPA examples

This module demonstrates different ways to use Spring Data JPA, from creating a
repository directly to using repositories in a Spring Boot application. Each
package has a corresponding test that exercises the feature it demonstrates.

Start with [`simple`](src/main/java/example/springdata/jpa/simple) and
[`SimpleUserRepositoryTests`](src/test/java/example/springdata/jpa/simple/SimpleUserRepositoryTests.java)
for basic repository usage, derived queries, declared queries, sorting, and
limiting results.

## Sample packages

| Package | Feature | Tests |
| --- | --- | --- |
| `basics` (test sources) | Create `SimpleJpaRepository` directly or obtain a repository through `JpaRepositoryFactory`, without a Spring application context. | [BasicSample](src/test/java/example/springdata/jpa/basics/BasicSample.java), [BasicFactorySetup](src/test/java/example/springdata/jpa/basics/BasicFactorySetup.java) |
| `simple` | Query methods, asynchronous queries, streaming results, and specifications. | [SimpleUserRepositoryTests](src/test/java/example/springdata/jpa/simple/SimpleUserRepositoryTests.java), [SpecificationTests](src/test/java/example/springdata/jpa/simple/SpecificationTests.java) |
| `auditing` | Track the user who created or modified an entity with `AuditorAware`. | [AuditableUserSample](src/test/java/example/springdata/jpa/auditing/AuditableUserSample.java) |
| `caching` | Integrate repository methods with Spring's caching abstraction, including cache eviction. | [CachingRepositoryTests](src/test/java/example/springdata/jpa/caching/CachingRepositoryTests.java) |
| `custom` | Add a custom repository method with a JPA or JDBC implementation. | [UserRepositoryCustomizationTests](src/test/java/example/springdata/jpa/custom/UserRepositoryCustomizationTests.java) |
| `customall` | Configure a custom base class for all repositories in a configuration. | [UserRepositoryCustomizationTests](src/test/java/example/springdata/jpa/customall/UserRepositoryCustomizationTests.java) |
| `compositions` | Compose a repository from multiple fragments, including a custom `save` implementation. | [ComposedRepositoryTests](src/test/java/example/springdata/jpa/compositions/ComposedRepositoryTests.java) |
| `projections` | Return interface and DTO projections, including dynamically selected projection types. | [CustomerRepositoryIntegrationTest](src/test/java/example/springdata/jpa/projections/CustomerRepositoryIntegrationTest.java) |
| `pagination` | Retrieve pages, slices, and scroll windows using offset or keyset scrolling. | [PaginationTests](src/test/java/example/springdata/jpa/pagination/PaginationTests.java) |

The `simple` package also contains
[`VirtualThreadsTests`](src/test/java/example/springdata/jpa/simple/VirtualThreadsTests.java),
which demonstrates repository calls and asynchronous queries on virtual threads.

## Running the examples

Use the JDK version configured in the [root `pom.xml`](../../pom.xml) (currently
Java 25). From the repository root, run this module's tests with the Maven wrapper:

```shell
./mvnw -pl jpa/example -am test
```

The tests use the embedded HSQLDB dependency from the [JPA parent](../pom.xml).
The `basics` tests configure their persistence unit in
[`META-INF/persistence.xml`](src/test/resources/META-INF/persistence.xml).
No separately running database is needed for this module.
