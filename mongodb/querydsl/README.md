# Spring Data MongoDB - Querydsl example

This project contains samples of [Querydsl](https://github.com/querydsl/querydsl) usage in Spring Data MongoDB.

Querydsl is a framework which enables the construction of fluent, type-safe queries for multiple backends including MongoDB.
Spring Data integrates with Querydsl via `QuerydslPredicateExecutor` and its reactive counterpart `ReactiveQuerydslPredicateExecutor`. 

**NOTE**: You may have to run `mvn compile` to generate the required `Q` classes first.

## Imperative

```java
interface CustomerQuerydslRepository 
        extends CrudRepository<Customer, String>, QuerydslPredicateExecutor<Customer> { }

@Autowired CustomerQuerydslRepository repository;

// ...

List<Customer> result = repository.findAll(QCustomer.customer.lastname.eq("Matthews"));
```

## Reactive

```java
interface ReactiveCustomerQuerydslRepository
		extends ReactiveCrudRepository<Customer, String>, ReactiveQuerydslPredicateExecutor<Customer> { }
		
@Autowired ReactiveCustomerQuerydslRepository repository;

// ...

Flux<Customer> result = repository.findAll(QCustomer.customer.lastname.eq("Matthews"));
```
