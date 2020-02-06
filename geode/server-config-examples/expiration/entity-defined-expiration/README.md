# Entity Defined Expiration Example

In this example expiration is configured on the [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) server by the domain object using the `@IdleTimeoutExpiration` and `@TimeToLiveExpiration` annotations.

To run the example simply run the tests located under expiration/entity-defined-expiration/src/test in your IDE.

## Running the example

The example is broken up into multiple steps:
1. Insert (Put) a Customer entries into the `Customers` region using the repositories `save` method.
2. Print current timestamp.
3. Constantly poll the value until its Time-To-Live expires.
4. Print current timestamp.
5. Insert (Put) a Customer entries into the `Customers` region using the repositories `save` method.
6. Print current timestamp.
7. Wait for 2 seconds to cause the entry to expire for being idle.
8. Print current timestamp.

Your test output should contain output similar to the following:

    Starting TTL wait period: 13/09/2019 04:30:41:154
    Ending TTL wait period: 13/09/2019 04:30:45:177
    Starting Idle wait period: 13/09/2019 04:30:45:179
    Ending Idle wait period: 13/09/2019 04:30:47:187
    
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.