# WAN Substitution Filter Example

In this example the `GatewaySender` is configured with  a `GatewayEventSubstitutionFilter` which replaces the value of `lastName` with the first initial while replicating entries to the other [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) server.

The two servers will be referred to as siteA and siteB. The test will connect to siteA to validate that entries are replicated to it from siteB.

To run the example simply run the tests located under wan/wan-substitution-filter/src/test in your IDE.

## Running the example

The example is broken up into multiple steps:
1. siteB inserts (Put) 300 Customer entries into the `Customers` region using the repositories `save` method.
2. A `GatewayEventSubstitutionFilter` implementation intercepts the entries and changes all last names to last initials when being replicated to siteA. Last names on siteB are unchanged.
3. Print the number of entries replicated to siteA.

Your test output should contain output similar to the following:

    [FORK] - Inserting 300 customers
    ...
    
    300 entries replicated to siteA
    All customers' last names changed to last initial on siteA
    
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.