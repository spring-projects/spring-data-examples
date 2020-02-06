# Security Example

In this example a [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) client and server will set up with security (username/password) authentication using Geode Security and Apache Shiro.

To run the example simply run the tests located under security/src/test in your IDE. There are two test files; one uses Geode Security and the other uses Apache Shiro. Both versions should behave the same.

The client is configured to connect to the server on `localhost` port `40404`. There is no need to start the server separately, as that is taken care of by the test.

## Running the example

The example is broken up into multiple steps:
1. Insert (Put) three Customer entries into the `Customers` region using the repositories `save` method.
2. Print the customers on the server.

Your output from the test `customerRepositoryWasAutoConfiguredCorrectly` should look similar to the following:

    [Inserting 3 entries for keys: 1, 2, 3
     [FORK] - [info 2019/09/06 10:20:39.187 PDT <ServerConnection on port 50646 Thread 0> tid=0x47] In region [Customers] created key [1] value [Customer(id=1, emailAddress=EmailAddress(value=2@2.com), firstName=John, lastName=Smith)]
     [FORK] - 
     [FORK] - [info 2019/09/06 10:20:39.191 PDT <ServerConnection on port 50646 Thread 0> tid=0x47] In region [Customers] created key [2] value [Customer(id=2, emailAddress=EmailAddress(value=3@3.com), firstName=Frank, lastName=Lamport)]
     [FORK] - 
     [FORK] - [info 2019/09/06 10:20:39.193 PDT <ServerConnection on port 50646 Thread 0> tid=0x47] In region [Customers] created key [3] value [Customer(id=3, emailAddress=EmailAddress(value=5@5.com), firstName=Jude, lastName=Simmons)]
     [FORK] - 
     Customers saved on server:
     	 Entry: 
      		 Customer(id=1, emailAddress=EmailAddress(value=2@2.com), firstName=John, lastName=Smith)
     	 Entry: 
      		 Customer(id=2, emailAddress=EmailAddress(value=3@3.com), firstName=Frank, lastName=Lamport)
     	 Entry: 
      		 Customer(id=3, emailAddress=EmailAddress(value=5@5.com), firstName=Jude, lastName=Simmons)
      		 
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.