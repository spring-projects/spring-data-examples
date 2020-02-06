# Function Invocation Example

In this example a [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) client will invoke remote functions registered on the server.

To run the example simply run the tests located under function-invocation/src/test in your IDE.

The client is configured to connect to the deployed/started server on `localhost` port `40404`.

## Running the example

The example is broken up into multiple steps:
1. Insert (Put) three Customer entries into the `Customers` region using the repositories `save` method.
2. Insert (Put) three Product entries into the `Products` region using the repositories `save` method.
3. Insert (Put) 100 Order entries into the `Orders` region using the repositories `save` method.

Your output from the test `functionsExecuteCorrectly` should look similar to the following:

    Inserting 3 entries for keys: 1, 2, 3
    [FORK] - [info 2019/09/06 09:24:49.274 PDT <ServerConnection on port 49925 Thread 0> tid=0x47] In region [Customers] created key [1] value [Customer(id=1, emailAddress=EmailAddress(value=2@2.com), firstName=John, lastName=Smith)]
    [FORK] - 
    [FORK] - [info 2019/09/06 09:24:49.278 PDT <ServerConnection on port 49925 Thread 0> tid=0x47] In region [Customers] created key [2] value [Customer(id=2, emailAddress=EmailAddress(value=3@3.com), firstName=Frank, lastName=Lamport)]
    [FORK] - 
    [FORK] - [info 2019/09/06 09:24:49.279 PDT <ServerConnection on port 49925 Thread 0> tid=0x47] In region [Customers] created key [3] value [Customer(id=3, emailAddress=EmailAddress(value=5@5.com), firstName=Jude, lastName=Simmons)]
    [FORK] - 
    All customers for emailAddresses:3@3.com,2@2.com using function invocation: 
    	 [Customer(id=2, emailAddress=EmailAddress(value=3@3.com), firstName=Frank, lastName=Lamport), Customer(id=1, emailAddress=EmailAddress(value=2@2.com), firstName=John, lastName=Smith)]
    Running function to sum up all product prices: 
    	1499.97
    Running function to sum up all order lineItems prices for order 1: 
    	2399.96
    For order: 
    	 Order(id=1, customerId=3, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    	LineItems:[Purchased 1 of Product Apple macBook at 899.99 for total of 899.99, Purchased 3 of Product Apple iPad at 499.99 for total of 1499.97]
   
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.

NOTE: Number of products purchased and total cost may vary from the above run as they are determined randomly.