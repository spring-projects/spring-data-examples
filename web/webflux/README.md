# Spring WebFlux Example

This example uses Spring WebFlux with Spring Data modules to explain reactive data access.

## Running the Example

The example uses an embedded MongoDB and H2 database to store its data.

## MongoDB

For MongoDB we have simple `Person` domain object along a reactive repository:

```
@Data
@NoArgsConstructor
@Document
public class Person {

	@Id String id;
	String firstname, lastname;

	public Person(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}
}

public interface ReactivePersonRepository extends ReactiveCrudRepository<Person, String> {

	Mono<Person> findByFirstnameIgnoringCase(String name);
}
```

The repository itself is used in `PersonController` to expose objects stored in MongoDB through a HTTP API:

```java
@RestController
@RequiredArgsConstructor
public class PersonController {

	final ReactivePersonRepository personRepository;

	@GetMapping("people")
	Flux<Person> getPeople() {
		return personRepository.findAll();
	}
}
```

Issue a HTTP GET to access objects stored in MongoDB:

```http
GET http://localhost:8080/people

HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
transfer-encoding: chunked

[
    {
        "firstname": "Walter",
        "id": "5afe94d67cb4b006754d8a01",
        "lastname": "White"
    },
    {
        "firstname": "Hank",
        "id": "5afe94d67cb4b006754d8a02",
        "lastname": "Schrader"
    },
    {
        "firstname": "Jesse",
        "id": "5afe94d67cb4b006754d8a03",
        "lastname": "Pinkman"
    }
]
```

## JPA

JPA and JDBC are blocking APIs and there's no reactive repository support. `JpaExecutor` allows adoption of blocking resource calls by offloading work to a `Scheduler` (threadpool). In this example, we store bank accounts in a relational database.

```java
@Data
@NoArgsConstructor
@Entity
public class BankAccount {

	@Id @GeneratedValue Long accountNumber;
	String owner; // Reference to Person.id
	BigDecimal balance;

	public BankAccount(String owner, BigDecimal balance) {
		this.owner = owner;
		this.balance = balance;
	}
}

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {

	BankAccount findByOwner(String id);
}
```

In this example, we allow submitting transactions that change the balance of the involved parties. Consider the following request: 

```http
PUT /transactions HTTP/1.1
Content-Type: application/json

{
    "amount": "1000",
    "sender": "Hank",
    "recipient": "Walter"
}

HTTP/1.1 200 OK
```

This request above sends the amount of `1000` from `Hank` to `Walter` within a managed JPA transaction. Issuing requests to obtain the actual accounts shows the modified balance.

```http
GET /people/Walter/bank-account HTTP/1.1


HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
    "accountNumber": 1,
    "balance": 1001000.0,
    "owner": "5afea5ed7cb4b00ed32c577b"
}
```

```http
GET /people/Hank/bank-account HTTP/1.1


HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
    "accountNumber": 2,
    "balance": 999000.0,
    "owner": "5afea5ed7cb4b00ed32c577c"
}
```

The code to process the transaction is located in `TransactionController`. The `storeTransaction(…)` method looks up `Person` objects
to determine the related `BankAccount`. Accounts are updated within a `doInTransaction(…)` callback to ensure atomicity.

```java
@RestController
@RequiredArgsConstructor
public class TransactionController {

	final ReactivePersonRepository personRepository;
	final BankAccountRepository bankAccountRepository;
	final JpaExecutor jpaExecutor;

	@PutMapping("transactions")
	Mono<Void> storeTransaction(@RequestBody Transaction transaction) {

		return personRepository.findByFirstnameIgnoringCase(transaction.getSender())
				.zipWith(personRepository.findByFirstnameIgnoringCase(transaction.getRecipient())) //
				.flatMap(it -> {

					Person owner = it.getT1();
					Person recipient = it.getT2();

					return jpaExecutor.transactional().doInTransaction(bankAccountRepository, repo -> {

						BankAccount from = repo.findByOwner(owner.getId());
						BankAccount to = repo.findByOwner(recipient.getId());

						if (from.getBalance().compareTo(transaction.getAmount()) < 0) {
							throw new IllegalStateException("Sender account does not have enough funding!");
						}

						from.setBalance(from.getBalance().subtract(transaction.getAmount()));
						to.setBalance(to.getBalance().add(transaction.getAmount()));

						repo.save(from);
						repo.save(to);

						return null;
					});
				}) //
				.then();
	}
}
```
