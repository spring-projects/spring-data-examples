# Transactions Example

In this example a [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) client will be configured to perform the basic CRUD operations within a transaction. First, it will do a successful transaction where entries are saved to the server, and then a failed transaction where all changes are reverted.

To run the example simply run the tests located under transactions/src/test in your IDE.

The client is configured to connect to the server on `localhost` port `40404`. There is no need to start the server separately, as that is taken care of by the test.

## Running the example

The example is broken up into multiple steps:
1. Print number of entries on server
3. Insert (Put) five Customer entries into the `Customers` region using the repositories `save` method.
3. Printing out the size of the region on the server
4. Update the Customer for id=2 in a transaction. Recording the before and after Customer detail.
5. Fail a transaction to update the Customer with id=2. Recording the before and after Customer detail.
6. Update the Customer for id=2 with a delay of 1000 ms.
7. Update the Customer for id=2 with a delay of 10 ms.
8. Print customer for id=2

NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.