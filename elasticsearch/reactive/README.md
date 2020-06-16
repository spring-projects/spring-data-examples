# Spring Data Elasticsearch - Reactive Examples

The `ReactiveElasticsearchClient` is a client based on `WebClient`. 
It uses the request/response objects provided by the Elasticsearch core project. 
Calls are directly operated on the reactive stack, not wrapping async (thread pool bound) responses into reactive types.

```java
@SpringBootApplication
class ApplicationConfiguration {}
```

The `ReactiveElasticsearchClient` can be used with the `ReactiveElasticsearchOperations` and `ReactiveElasticsearchRepository`.

```java
@Autowired ReactiveElasticsearchOperations operations;

// ...

CriteriaQuery query = new CriteriaQuery("keywords").contains("java");

Flux<Conference> result = operations.find(query, Conference.class);
```

```java
interface ConferenceRepository extends ReactiveCrudRepository<Conference, String> {
 
    Flux<Conference> findAllByKeywordsContains(String keyword);
}

// ...

@Autowired ConferenceRepository repository;

// ...

Flux<Conference> result = repository.findAllByKeywordsContains("java");
```


**Requirements:**

 * [Maven](http://maven.apache.org/download.cgi)
 * [Elasticsearch](https://www.elastic.co/de/downloads/elasticsearch)

**Running Elasticsearch** 

```bash
$ cd elasticsearch
$ ./bin/elasticsearch
```


