# Compression Example

n this example the [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) server is configured with `SnappyCompressor` to store region entries in a compressed format.

To run the example simply run the tests located under compression/src/test in your IDE.

## Running the example

The example is broken up into multiple steps:
1. Insert (Put) 4000 Customer entries into the `Customers` region using the repositories `save` method.
2. The entries are compressed by `SnappyCompressor`.

Your test output should contain the following:

    Inserting 4000 Customers into compressed region
    
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.