# Spring Data Redis - Streams Examples

The [Redis Stream](https://redis.io/topics/streams-intro) is a new data type introduced with Redis 5.0 modelling log data structure.
Spring Data Redis supports _Redis Streams_ via both the imperative and the reactive API.

## Imperative API

**Basic Usage**
```java
@Autowired 
RedisTemplate template;

StringRecord record = StreamRecords.string(…)
    .withStreamKey("my-stream");
RecordId id = template.streamOps().add(record);

List<...> records = template.streamOps().read(count(2), from(id));
```

**ContinuousRead Read**
```java
@Autowired 
RedisConnectionFactory factory;

StreamListener<String, MapRecord<…> listener = 
  (msg) -> {
    // ...
  };

StreamMessageListenerContainer container = StreamMessageListenerContainer.create(factory));

container.receive(StreamOffset.fromStart("my-stream"), listener);
```

## Reactive API

**Basic Usage**
```java
@Autowired 
ReactiveRedisTemplate template;

StringRecord record = StreamRecords.string(…)
    .withStreamKey("my-stream");
Mono<RecordId> id = template.streamOps().add(record);

Flux<...> records = template.streamOps().read(count(2), from(id));
```

**ContinuousRead Read**
```java
@Autowired 
ReactiveRedisConnectionFactory factory;

StreamReceiver receiver = StreamReceiver.create(factory));

container.receive(StreamOffset.fromStart("my-stream"))
    .doOnNext((msg) -> {
    	// ...
    })
    .subscribe();
```
