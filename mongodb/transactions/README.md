# Spring Data MongoDB - Transaction Sample

This project contains samples for upcoming MongoDB 4.0 transactions.

## Running the Sample

The sample uses multiple embedded MongoDB processes in a [MongoDB replica set](https://docs.mongodb.com/manual/replication/).
It contains test for both the synchronous and the reactive transaction support in the `sync` / `reactive` packages.   

You may run the examples directly from your IDE or use maven on the command line.

**INFO:** The operations to download the required MongoDB binaries and spin up the cluster can take some time. Please 
be patient. 

## Sync Transactions

`MongoTransactionManager` is the gateway to the well known Spring transaction support. It lets applications use 
[the managed transaction features of Spring](http://docs.spring.io/spring/docs/{springVersion}/spring-framework-reference/html/transaction.html).
The `MongoTransactionManager` binds a `ClientSession` to the thread. `MongoTemplate` detects the session and operates
on these resources which are associated with the transaction accordingly. `MongoTemplate` can also participate in 
other, ongoing transactions.

```java
@Configuration
static class Config extends AbstractMongoConfiguration {

	@Bean
	MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}

	// ...
}

@Component
public class TransitionService {

    @Transactional
    public void run(Integer id) {
    
    	Process process = lookup(id);
    
    	if (!State.CREATED.equals(process.getState())) {
    		return;
    	}
    
    	start(process);
    	verify(process);
    	finish(process);
    }
}
```

## Reactive transactions

`ReactiveMongoTemplate` offers dedicated methods for operating within a transaction without having to worry about the
commit/abort actions depending on the operations outcome. There's currently no session or transaction integration 
with reactive repositories - we apologize for that!

**NOTE:** Please note that you cannot preform meta operations, like collection creation within a transaction.

```java
@Component
public class RactiveTransitionService {
	
    public Mono<Integer> run(Integer id) {

		return template.inTransaction().execute(action -> {

			return lookup(id) //
			        .filter(State.CREATED::equals)
			        .flatMap(process -> start(action, process))
			        .flatMap(this::verify)
			        .flatMap(process -> finish(action, process));

		}).next().map(Process::getId);
	}
}
```