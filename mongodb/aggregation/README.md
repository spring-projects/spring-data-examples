# Spring Data MongoDB - Aggregations

This project contains samples for using MongoDB [Aggregations](https://docs.mongodb.com/manual/aggregation/) 
showing both the programmatic and declarative approach for integrating an Aggregation Pipeline into a repository.

## Programmatic

The programmatic approach uses a [custom repository](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#repositories.custom-implementations) implementation along with the [Aggregation Framework](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongo.aggregation).

```java
class OrderRepositoryImpl implements OrderRepositoryCustom {

	private MongoOperations operations;
	
	// ...

	@Override
	public Invoice getInvoiceFor(Order order) {

		AggregationResults<Invoice> results = operations.aggregate(newAggregation(Order.class, 
			match(where("id").is(order.getId())), 
			unwind("items"), 
			project("id", "customerId", "items") 
				.andExpression("'$items.price' * '$items.quantity'").as("lineTotal"), 
			group("id") 
				.sum("lineTotal").as("netAmount") 
				.addToSet("items").as("items"), 
			project("id", "items", "netAmount") 
				.and("orderId").previousOperation() 
				.andExpression("netAmount * [0]", taxRate).as("taxAmount") 
				.andExpression("netAmount * (1 + [0])", taxRate).as("totalAmount") 
		), Invoice.class);

		return results.getUniqueMappedResult();
	}
}
```

## Declarative

The [declarative approach](https://docs.spring.io/spring-data/mongodb/docs/2.2.0.RC2/reference/html/#mongodb.repositories.queries.aggregation) allows to define an Aggregation Pipeline via the `@Aggregation` annotation.

```java
public interface OrderRepository extends CrudRepository<Order, String>, OrderRepositoryCustom {

	@Aggregation("{ $group : { _id : $customerId, total : { $sum : 1 } } }")
	List<OrdersPerCustomer> totalOrdersPerCustomer(Sort sort);

	@Aggregation(pipeline = { "{ $match : { customerId : ?0 } }", "{ $count : total }" })
	Long totalOrdersForCustomer(String customerId);
}
```

