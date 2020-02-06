# WAN Substitution Filter Example

In this example the `GatewaySender` is configured with  a `GatewayTransportFilter` which performs checking on the stream used fr replicating entries between [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) servers.

The two servers will be referred to as siteA and siteB. The test will connect to siteA to validate that entries are replicated to it from siteB.

To run the example simply run the tests located under wan/wan-transport-filters/src/test in your IDE.

## Running the example

The example is broken up into multiple steps:
1. siteB inserts (Put) 300 Customer entries into the `Customers` region using the repositories `save` method.
2. A `GatewayTransportFilter` implementation intercepts and encrypts the entries being replicated to siteA.
3. Print the number of entries replicated to siteA.

Your test output should contain output similar to the following:

    [FORK] - Inserting 300 customers
    ...
    
    [FORK] - CheckedTransportFilter: Getting output stream
    [FORK] - CheckedTransportFilter: Getting input stream
    [FORK] - CheckedTransportFilter: Getting output stream
    [FORK] - CheckedTransportFilter: Getting input stream
    [FORK] - CheckedTransportFilter: Getting output stream
    [FORK] - CheckedTransportFilter: Getting input stream
    [FORK] - CheckedTransportFilter: Getting output stream
    [FORK] - CheckedTransportFilter: Getting input stream
    [FORK] - CheckedTransportFilter: Getting output stream
    [FORK] - CheckedTransportFilter: Getting input stream
    ...
    
    300 entries replicated to siteA
    
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.