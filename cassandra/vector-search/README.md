# Spring Data for Apache Cassandra - Vector Search Example

This project
contains [Vector Search](https://docs.spring.io/spring-data/cassandra/reference/5.0/cassandra/repositories/vector-search.html)
with Spring Data for Apache Cassandra.

## Vector Support

The Spring Data `Vector` type can be used in repository query methods.
Domain type properties of managed domain types are required to use a numeric array representation for embeddings.

```java

@Table
public class Comment {

    @Id
    private String id;

    private String country;
    private String description;

    @SaiIndexed
    @VectorType(dimensions = 5)
    private Vector embedding;

    // ...
}


public interface CommentRepository extends Repository<Comment, String> {

    SearchResults<Comment> searchTop10ByEmbeddingNear(Vector embedding, ScoringFunction function);
}
```

This example contains a test class to illustrate vector search with a Repository in `CassandraVectorSearchTest`.
