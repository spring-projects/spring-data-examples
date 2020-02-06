# WAN Event Filters Example

In this example the `GatewaySender` is configured with  a `GatewayEventFilter` which only lets entries with even key values be replicated to the other [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) server.

The two servers will be referred to as siteA and siteB. The test will connect to siteA to validate that entries are replicated to it from siteB.

To run the example simply run the tests located under wan/wan-event-filters/src/test in your IDE.

## Running the example

The example is broken up into multiple steps:
1. siteB inserts (Put) 300 Customer entries into the `Customers` region using the repositories `save` method.
2. A `GatewayEventFilter` implementation intercepts the entries and stops entries with odd keys from being replicated to siteA.
3. Print the number of entries replicated to siteA.

Your test output should contain output similar to the following:

    [FORK] - Inserting 300 customers
    ...
    
    150 entries replicated to siteA
    
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.