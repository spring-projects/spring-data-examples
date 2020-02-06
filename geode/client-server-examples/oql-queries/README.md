# OQL Queries Example

In this example a [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) client will perform OQL queries. This example utilizes both GemFireTemplate and GemFireRepositories to query and implements indexes to increase query performance.

To run the example simply run the tests located under oql-queries/src/test in your IDE.

The client is configured to connect to the server on `localhost` port `40404`. There is no need to start the server separately, as that is taken care of by the test.

## Running the example

The example is broken up into multiple steps:
1. Insert (Put) three Customer entries into the `Customers` region using the repositories `save` method.
2. Retrieve and print customers using a repository.
3. Retrieve and print customers using a template.
4. Retrieve and print customers using an index.


Your output from the test `customerRepositoryWasAutoConfiguredCorrectly` should look similar to the following:

    Inserting 3 entries for keys: 1, 2, 3
    [FORK] - [info 2019/09/06 09:30:50.514 PDT <ServerConnection on port 50101 Thread 0> tid=0x47] In region [Customers] created key [1] value [Customer(id=1, emailAddress=EmailAddress(value=2@2.com), firstName=John, lastName=Smith)]
    [FORK] - 
    [FORK] - [info 2019/09/06 09:30:50.518 PDT <ServerConnection on port 50101 Thread 0> tid=0x47] In region [Customers] created key [2] value [Customer(id=2, emailAddress=EmailAddress(value=3@3.com), firstName=Frank, lastName=Lamport)]
    [FORK] - 
    [FORK] - [info 2019/09/06 09:30:50.519 PDT <ServerConnection on port 50101 Thread 0> tid=0x47] In region [Customers] created key [3] value [Customer(id=3, emailAddress=EmailAddress(value=5@5.com), firstName=Jude, lastName=Simmons)]
    [FORK] - 
    Find customer with key=2 using GemFireRepository: Customer(id=2, emailAddress=EmailAddress(value=3@3.com), firstName=Frank, lastName=Lamport)
    Find customer with key=2 using GemFireTemplate: [Customer(id=2, emailAddress=EmailAddress(value=3@3.com), firstName=Frank, lastName=Lamport)]
    [FORK] - [info 2019/09/06 09:30:50.639 PDT <ServerConnection on port 50101 Thread 0> tid=0x47] In region [Customers] updated key [1] [oldValue [Customer(id=1, emailAddress=EmailAddress(value=3@3.com), firstName=Jude, lastName=Smith)]] new value [Customer(id=1, emailAddress=EmailAddress(value=3@3.com), firstName=Jude, lastName=Smith)]
    [FORK] - 
    [FORK] - [info 2019/09/06 09:30:50.648 PDT <ServerConnection on port 50101 Thread 0> tid=0x47] Query Executed in 0.776359 ms; rowCount = 2; indexesUsed(0) "<TRACE> <HINT 'emailAddressIndex'> select * from /Customers customer where customer.emailAddress.value = $1 LIMIT 100"
    [FORK] - 
    Find customers with emailAddress=3@3.com: [Customer(id=1, emailAddress=EmailAddress(value=3@3.com), firstName=Jude, lastName=Smith), Customer(id=2, emailAddress=EmailAddress(value=3@3.com), firstName=Frank, lastName=Lamport)]
    [FORK] - [info 2019/09/06 09:30:50.653 PDT <ServerConnection on port 50101 Thread 0> tid=0x47] Query Executed in 0.311832 ms; rowCount = 1; indexesUsed(0) "<TRACE> select * from /Customers customer where customer.firstName = $1 LIMIT 100"
    [FORK] - 
    Find customers with firstName=Frank: [Customer(id=2, emailAddress=EmailAddress(value=3@3.com), firstName=Frank, lastName=Lamport)]
    [FORK] - [info 2019/09/06 09:30:50.655 PDT <ServerConnection on port 50101 Thread 0> tid=0x47] Query Executed in 0.241992 ms; rowCount = 2; indexesUsed(0) "<TRACE> select * from /Customers customer where customer.firstName = $1 LIMIT 100"
    [FORK] - 
    Find customers with firstName=Jude: [Customer(id=1, emailAddress=EmailAddress(value=3@3.com), firstName=Jude, lastName=Smith), Customer(id=3, emailAddress=EmailAddress(value=5@5.com), firstName=Jude, lastName=Simmons)]
    Find customers with firstName=Jude on local client region: []
    
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.