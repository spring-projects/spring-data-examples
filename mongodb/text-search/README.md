# Spring Data MongoDB - Text Search Examples

This project contains samples of text search specific features of Spring Data Mongodb.

## Support for Text Index

Define text index structures manually (like below) or use `@TextIndexed` to mark content to be indexed for full text search.

```java
TextIndexDefinition textIndex = new TextIndexDefinitionBuilder()
  .onField("title", 3F)
  .onField("content", 2F)
  .onField("categories")
  .build();

template.indexOps(BlogPost.class).ensureIndex(textIndex);
```

## Support for full text repository queries

Use derived finder methods to search for terms and phrases.

```java
interface BlogPostRepository extends CrudRepository<BlogPost, String> {

    // page through results for full text query
	Page<BlogPost> findBy(TextCriteria criteria, Pageable page);

    // find all matching documents and sort by relevance
	List<BlogPost> findAllByOrderByScoreDesc(TextCriteria criteria);
}
```