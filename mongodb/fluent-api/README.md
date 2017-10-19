# Spring Data MongoDB 2.0 - FluentMongoOperations Example

This project contains usage samples of `FluentMongoOperations`.

## Fluent API

`FluentMongoOperations` provides a stripped down, more focused API alternative for classic `MongoOperations`.
The main entry points are typical tasks for finding and manipulating ``Document``s on a domain type base, while operations like creating indexes are left out.

For convenience classic `MongoOperations` extend `FluentMongoOperations`, however for most cases it might be sufficient to just work with the reduced interface.
To get started just inject `FluentMongoOperations`.

The entry point methods of `FluentMongoOperations` provide you with an immutable fluent API allowing only valid next steps while constructing the operation to execute. This allows to set up operations once and keep those in memory for multiple executions.

### Query

Looking at the following `query(SWCharacter.class)`.

`SWCharacter` is used for mapping the query properties to the actual document field names. As the `SWCharacter` defines `@Field("firstname") name;` the `where` clause of the query is mapped to the MongoDB representation as `{ firstname : "luke" }`.

Using `as(Jedi.class)` switches return type mapping from `SWCharacter` to `Jedi` which allows to map resulting documents to a different type than the one used for query mapping.

So far no actual query execution has been invoked. Calling on of the terminating methods like `one()`, `first()`, `all()`,... triggers the query.

```java
mongoOps.query(SWCharacter.class)
  .inCollection("star-wars")
  .as(Jedi.class)
  .matching(query(where("name").is("luke")))
  .one();

```

Different stages in the command essembly process allow to seamlessly switch to different API paths. Using `near` instead of `matching` switches to the path for geo queries requireing the presence of a `NearQuery` while altering the command result type from `List` to `GeoResults` and limiting terminating operations to just `all()`.

```java

NearQuery alderaanWithin3Parsecs = NearQuery.near(-73.9667, 40.78)
  .maxDistance(new Distance(3, MILES))
  .spherical(true);

GeoResults<Jedi> results = mongoOps.query(SWCharacter.class)
  .as(Jedi.class)
  .near(alderaanWithin3Parsecs)
  .all();
```


### Update

Looking at the following `update(Jedi.class)`.

`Jedi` already defines the `collection` via the `@Document` annotation, so there is no need to explicitly specify a collection name via `inCollection(String)`. The `Jedi` domain type is also used for query and update mapping.

So far no actual query execution has been invoked. Calling on of the terminating methods like `all()`, `upsert()`, `findAndModify()`, etc. triggers the update.
```java


mongoOps.update(Jedi.class)
  .matching(query(where("lastname").is("windu")))
  .apply(update("name", "mence"))
  .upsert();
```
