# Spring Data MongoDB - Change Streams Example

This project contains usage samples for consuming MongoDB 3.6 [Change Streams](https://docs.mongodb.com/manual/changeStreams/) using the imperative as well as the reactive MongoDB Java drivers.

### Imperative Style

Change stream events can be consumed using a `MessageListener` registered within a `MessageListenerContainer`. The container takes care of running the task in a separate `Thread` pushing events to the `MessageListener`.

```java
@Configuration
class Config {

	@Bean
	MessageListenerContainer messageListenerContainer(MongoTemplate template) {
		return new DefaultMessageListenerContainer(template);
	}
}
```

Once the `MessageListenerContainer` is in place `MessageListeners` can be registered.

```java
MessageListener<ChangeStreamDocument<Document>, Person> messageListener = (message) -> {
	System.out.println("Hello " + message.getBody().getFirstname());
};

ChangeStreamRequest<Person> request = ChangeStreamRequest.builder()
	.collection("person")
	.filter(newAggregation(match(where("operationType").is("insert"))))
	.publishTo(messageListener)
	.build();

Subscription subscription = messageListenerContainer.register(request, Person.class);

// ...
```

### Reactive Style

Change stream events be directly consumed via a `Flux` connected to the change stream.

```java
Flux changeStream = reactiveTemplate
	.changeStream(newAggregation(match(where("operationType").is("insert"))),
				Person.class, ChangeStreamOptions.empty(), "person");
				
changeStream.doOnNext(event -> System.out.println("Hello " + event.getBody().getFirstname()))
	.subscribe();
```