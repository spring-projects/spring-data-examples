# Spring Data JPA - JPA 2.1 example

This project contains samples of JPA 2.1 specific features of Spring Data JPA.

## Support for declarative Fetch Graphs customization

You can customize the loading of entity associations via EntityGraphs. JPA 2.1 provides the `NamedEntityGraph` annotation
that allows you define fetching behavior in a flexible way.

In Spring Data JPA we support to specify which fetch-graph to use for a repository query method via the `EntityGraph` annotation.

You can refer to a fetch graph by name like in the following example.
```java
@EntityGraph("product-with-tags")
Product findOneById(Long id);
```

We also offer an alternative and more concise way to declarativly specify a fetch graph for a repository query method in an 
ad-hoc manner:
```java
@EntityGraph(attributePaths = "tags")
Product getOneById(Long id);
```
By explicitly specifying which associations to fetch via the `attributePaths` attribute you don't need to specify a 
`NamedEntityGraph` annotation on your entity :)

## Support for stored procedure execution

You can execute stored procedures either predefined using the JPA 2.1 mapping annotations or dynamically let the stored procedure definition be derived from the repository method name.

Stored procedure declaration in the database (schema.sql):

```sql
DROP procedure IF EXISTS plus1inout
/;
CREATE procedure plus1inout (IN arg int, OUT res int)
BEGIN ATOMIC
    set res = arg + 1;
END
/;
```

JPA 2.1 stored procedure declaration:

```java
@Entity
@NamedStoredProcedureQuery(name = "User.plus1", procedureName = "plus1inout", parameters = {
  @StoredProcedureParameter(mode = ParameterMode.IN, name = "arg", type = Integer.class),
  @StoredProcedureParameter(mode = ParameterMode.OUT, name = "res", type = Integer.class) })
public class User {

    @Id @GeneratedValue//
    private Long id;
}

```

Spring Data JPA repository declaration to execute procedures:

```java
public interface UserRepository extends CrudRepository<User, Long> {

  // Explicitly mapped to named stored procedure {@code User.plus1} in the {@link EntityManager}.
  // By default, we would've try to find a procedure declaration named User.plus1BackedByOtherNamedStoredProcedure
  @Procedure(name = "User.plus1")
  Integer plus1BackedByOtherNamedStoredProcedure(@Param("arg") Integer arg);

  // Directly map the method to the stored procedure in the database (to avoid the annotation madness on your domain classes).
  @Procedure
  Integer plus1inout(Integer arg);
}
```

Calling `UserRepository.plus1BackedByOtherNamedStoredProcedure(…)` will execute the stored procedure `plus1inout` using the meta-data declared on the `User` domain class.

`UserRepository.plus1inout(…)` will derive stored procedure metadata from the repository and default to positional parameter binding and expect a single output parameter of the backing stored procedure.

## Support for custom SqlResultSetMapping with ConstructorResult

Sometimes, e.g. for analytics, it is handy to be able to return a different entity result type from a Repository query method than the base Repository entity type or an interface based projection.

In those cases one can leverage JPAs `SqlResultSetMapping` feature to map the columns of the result of a query to different fields.

JPA 2.1 introduced the new `SqlResultSetMapping` type `ConstructorResult` which allows to map columns of a result set row to a constructor invocation
which can be nicely used in combination with Value Objects.

This example shows how to define a custom `SqlResultSetMapping` for the result of an analytical native query that reports the usage summary for a set of Subscriptions.

`SqlResultSetMapping` definition on the `Subscription` entity class:
```java
@Entity
@NoArgsConstructor
@SqlResultSetMapping(
  name = "subscriptionSummary",
  classes = @ConstructorResult(
    targetClass = SubscriptionSummary.class,
    columns = {
      @ColumnResult(name = "productName", type = String.class),
      @ColumnResult(name = "subscriptions", type = long.class)
}))
@NamedNativeQuery(
  name = "Subscription.findAllSubscriptionSummaries",
  query = "select product_name as productName, count(user_id) as subscriptions from subscription group by product_name order by productName",
  resultSetMapping = "subscriptionSummary"
)
@Data
public class Subscription {
  …
}
```

`SubscriptionSummary` is modelled as a value object:
```java
@Value
public class SubscriptionSummary {

  String product;
  Long usageCount;
}
```

The `SubscriptionRepository` declares the custom query method `findAllSubscriptionSummaries` which is backed by the named native query declared on the `Subscription` entity.
```java
interface SubscriptionRepository extends CrudRepository<Subscription,Long> {

  @Query(nativeQuery = true)
  List<SubscriptionSummary> findAllSubscriptionSummaries();
}
```