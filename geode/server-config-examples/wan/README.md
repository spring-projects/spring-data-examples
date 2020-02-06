# WAN Example

In this example two [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) servers are deploy. One server populates itself with data and the other server gets populated with that data via WAN replication.

This example is a little different from the others because there are two servers instead of one. The two servers will be referred to as siteA and siteB. The test will connect to siteB to validate that entries are replicated to it.

To run the example simply run the tests located under wan/src/test in your IDE.

## Running the example

The example is broken up into multiple steps:
1. siteA inserts (Put) 300 Customer entries into the `Customers` region using the repositories `save` method.
2. Print the number of entries replicated to siteB

Your test output should contain output similar to the following:

    [FORK] - Inserting 301 entries on siteA
    
    301 entries replicated to siteB
    
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.

NOTE: Currently all tests in wan are marked with `@Ignore` so they WILL NOT run. This is because the tests start multiple 
servers and are unable to shut them down properly, meaning they may interfere with other tests run after them. You may 
remove the `@Ignored` to run the tests, but be advised that you may have to manually kill the server process(es) after.