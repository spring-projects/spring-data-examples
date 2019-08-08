# Spring Data Elasticsearch - High Level REST Client Examples

````java
class ApplicationConfiguration extends AbstractElasticsearchConfiguration {

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        return RestClients.create(ClientConfiguration.localhost()).rest();
    }
}
````

The `RestHighLevelClient` can be used with the `ElasticsearchOperations` and `ElasticsearchRepository`.

```java
@Autowired ElasticsearchOperations operations;

// ...

CriteriaQuery query = new CriteriaQuery("keywords").contains("java");

List<Conference> result = operations.find(query, Conference.class);
```

```java
interface ConferenceRepository extends ElasticsearchRepository<Conference, String> {
 
    List<Conference> findAllByKeywordsContains(String keyword);
}

// ...

@Autowired ConferenceRepository repository;

// ...

List<Conference> result = repository.findAllByKeywordsContains("java");
```


**Requirements:**

 * [Maven](http://maven.apache.org/download.cgi)
 * [Elasticsearch](https://www.elastic.co/de/downloads/elasticsearch)

**Running Elasticsearch** 

```bash
$ cd elasticsearch
$ ./bin/elasticsearch
```

