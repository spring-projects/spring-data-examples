# Spring Data MongoDB - Vector Search Example

This project
contains [Vector Search](https://docs.spring.io/spring-data/mongodb/reference/5.0/mongodb/repositories/vector-search.html)
with Spring Data MongoDB.

## Vector Support

The Spring Data `Vector` type can be used in repository query methods.
Domain type properties of managed domain types are required to use a numeric array representation for embeddings.

```java

@Document
public class Comment {

    @Id
    private ObjectId id;

    private String country;
    private String description;

    private Vector embedding;

    // ...
}


public interface CommentRepository extends Repository<Comment, String> {

    @VectorSearch(indexName = "cosine-index", searchType = VectorSearchOperation.SearchType.ANN)
    SearchResults<Comment> searchTop10ByCountryAndEmbeddingNear(String country, Vector vector, Score distance);
}
```

This example contains a test class to illustrate vector search with a Repository
in `VectorAppTest`.
