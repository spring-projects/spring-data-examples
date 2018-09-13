# Spring Data MongoDB 2.1 - Schema & Validation Example

MongoDB (as of version 3.2) supports validating documents against a given structure described by a query. 

```json
{
    "name" : {
        "$exists" : true,
        "$ne" : null,
        "$type" : 2
    },
    "age" : {
        "$exists" : true,
        "$ne" : null,
        "$type" : 16,
        "$gte" : 0,
        "$lte" : 125
    }
}
```

The structure can be built from `Criteria` objects in the same way as they are used for defining queries.

```java
Validator.criteria(where("name").exists(true).ne(null).type(2)
	.and("age").exists(true).ne(null).type(16).gte(0).lte(125));
```

MongoDB 3.6 supports collections that validate documents against a provided [JSON Schema](https://docs.mongodb.com/manual/core/schema-validation/#json-schema) that
complies with the JSON schema specification (draft 4).

```json
{
  "type": "object",
  "required": [ "name", "age" ],
  "properties": {
    "name": {
      "type": "string",
      "minLength": 1
    },
    "age": {
      "type": "int",
      "minimum" : 0,
      "exclusiveMinimum" : false,
      "maximum" : 125,
      "exclusiveMaximum" : false
    }
  }
}
```
The `MongoJsonSchema` and its builder allow fluent schema definition via the Java API.

```java
MongoJsonSchema schema = MongoJsonSchema.builder() //
    .required("name", "age") //
    .properties( //
        string("name").minLength(1), //
        int32("age").gte(0).lte(125) //
    ).build();
```

The schema can be used for various funcitionality: Set up `Document` validation for a collection:

```java
template.createCollection(Jedi.class, CollectionOptions.empty().validator(Validator.schema(schema)));
```

and to query the store for documents matching a given blueprint:

```java
template.find(query(matchingDocumentStructure(schema)), Jedi.class);
```
