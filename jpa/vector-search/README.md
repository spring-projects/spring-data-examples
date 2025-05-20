# Spring Data JPA - Vector Search Example

This project contains [Vector Search](https://docs.spring.io/spring-data/jpa/reference/4.0/repositories/vector-search.html) with Spring Data JPA and the `hibernate-vector` module.

## Vector Support

The Spring Data `Vector` type can be used in repository query methods.
Domain type properties of managed domain types are required to use a numeric array representation for embeddings.

```java

@Entity
@Table(name = "jpa_comment")
public class Comment {

    @Id
    @GeneratedValue private Long id;

    private String country;
    private String description;

    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 5)
    private float[] embedding;

    // ...
}


public interface CommentRepository extends Repository<Comment, String> {

    SearchResults<Comment> searchTop10ByCountryAndEmbeddingNear(String country, Vector vector, Score distance);
}
```

This example contains a test class to illustrate vector search with a Repository in `JpaVectorSearchTest`.
