# Security Example

In this example a [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) client and server will set up with security (username/password) authentication using Geode Security and Apache Shiro.

To run the example simply run the tests located under security/src/test in your IDE. There are two test files; one uses Geode Security and the other uses Apache Shiro. Both versions should behave the same.

The client is configured to connect to the server on `localhost` port `40404`. There is no need to start the server separately, as that is taken care of by the test.

## Running the example

The example is broken up into multiple steps:
1. Insert (Put) three Customer entries into the `Customers` region using the repositories `save` method.
2. Print the customers on the server.
      		 
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.