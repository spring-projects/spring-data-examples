# Continuous Queries Example

In this example a [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) client will put data into the region on the server, and register a continuous query on that data.

To run the example simply run the tests located under continuous-queries/src/test in your IDE.

The client is configured to connect to the server on `localhost` port `40404`. There is no need to start the server separately, as that is taken care of by the test.

## Running the example

The example is broken up into multiple steps:
1. Insert (Put) three Customer entries into the `Customers` region using the repositories `save` method.
2. Print "Received message for CQ 'CustomerCQ'" for each item retrieved by the continuous query.

Your output from the test `continuousQueryWorkingCorrectly` should look similar to the following:

    Inserting 3 entries for keys: 1, 2, 3
    [FORK] - [info 2019/09/05 16:45:40.282 PDT <ServerConnection on port 55918 Thread 0> tid=0x49] In region [Customers] created key [1] value [Customer(id=1, emailAddress=EmailAddress(value=2@2.com), firstName=John, lastName=Smith)]
    [FORK] - 
    [FORK] - [info 2019/09/05 16:45:40.286 PDT <ServerConnection on port 55918 Thread 0> tid=0x49] In region [Customers] created key [2] value [Customer(id=2, emailAddress=EmailAddress(value=3@3.com), firstName=Frank, lastName=Lamport)]
    [FORK] - 
    [FORK] - [info 2019/09/05 16:45:40.288 PDT <ServerConnection on port 55918 Thread 0> tid=0x49] In region [Customers] created key [3] value [Customer(id=3, emailAddress=EmailAddress(value=5@5.com), firstName=Jude, lastName=Simmons)]
    [FORK] - 
    Received message for CQ 'CustomerCQ'CqEvent [CqName=CustomerCQ; base operation=CREATE; cq operation=CREATE; key=1; value=Customer(id=1, emailAddress=EmailAddress(value=2@2.com), firstName=John, lastName=Smith)]
    Received message for CQ 'CustomerCQ'CqEvent [CqName=CustomerCQ; base operation=CREATE; cq operation=CREATE; key=2; value=Customer(id=2, emailAddress=EmailAddress(value=3@3.com), firstName=Frank, lastName=Lamport)]
    Received message for CQ 'CustomerCQ'CqEvent [CqName=CustomerCQ; base operation=CREATE; cq operation=CREATE; key=3; value=Customer(id=3, emailAddress=EmailAddress(value=5@5.com), firstName=Jude, lastName=Simmons)]
    
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.