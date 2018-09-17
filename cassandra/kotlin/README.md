# Spring Data Cassandra - Kotlin examples

This project contains samples of Kotlin-specific features of Spring Data (Cassandra).

## Value defaulting on entity construction

Kotlin allows defaulting for constructor- and method arguments. 
Defaulting allows usage of substitute values if a field in the document is absent or simply `null`. 
Spring Data inspects objects whether they are Kotlin types and uses the appropriate constructor.

```kotlin
@Table
data class Person(@PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED) val firstname: String? = "", val lastname: String = "White")

operations.cqlOperations.execute(QueryBuilder.insertInto("person").value("firstname", "Walter"))

val person = operations.query<Person>()
				.matching(query(where("firstname").isEqualTo("Walter")))
				.firstValue()!!

assertThat(person.lastname).isEqualTo("White")
```

## Kotlin Extensions

Spring Data exposes methods accepting a target type to either query for or to project results values on. 
Kotlin represents classes with its own type, `KClass` which can be an obstacle when attempting to obtain a Java `Class` type.

Spring Data ships with extensions that add overloads for methods accepting a type parameter by either leveraging generics or accepting `KClass` directly.

```kotlin
operations.getTableName<Person>()
 
operations.getTableName(Person::class)
```

## Nullability

Declaring repository interfaces using Kotlin allows expressing nullability constraints on arguments and return types. Spring Data evaluates nullability of arguments and return types and reacts to these. Passing `null` to a non-nullable argument raises an `IllegalArgumentException`, as you're already used to from Kotlin. Spring Data helps you also to prevent `null` in query results. If you wish to return a nullable result, use Kotlin's nullability marker `?`. To prevent `null` results, declare the return type of a query method as non-nullable. In the case a query yields no result, a non-nullable query method throws `EmptyResultDataAccessException`.  

```kotlin
interface PersonRepository : CrudRepository<Person, String> {

	/**
	 * Query method declaring a nullable return type that allows to return null values.
	 */
	fun findOneOrNoneByFirstname(firstname: String): Person?

	/**
	 * Query method declaring a nullable argument.
	 */
	fun findNullableByFirstname(firstname: String?): Person?

	/**
	 * Query method requiring a result. Throws [org.springframework.dao.EmptyResultDataAccessException] if no result is found.
	 */
	fun findOneByFirstname(firstname: String): Person
}
```
