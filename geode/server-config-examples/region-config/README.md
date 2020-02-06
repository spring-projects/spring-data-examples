# Region Config Example

In this example the [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) server makes use of `PartitionAttributes` to set the number of buckets and redundant copies.

To run the example simply run the tests located under region-config/src/test in your IDE.

## Running the example

The example is broken up into multiple steps:
1. Insert (Put) 3001 Customer entries into the `Customers` region using the repositories `save` method.
2. Insert (Put) 3 Product entries into the `Products` region using the repositories `save` method.
3. Insert (Put) 100 Order entries into the `Orders` region using the repositories `save` method.
4. Print the number of entries in the `Customers` region.
5. Print the number of entries in the `Products` region.
6. Print the number of entries in the `Orders` region.

Your test output should contain output similar to the following:

    There are 3001 customers
    There are 3 products
    There are 100 orders
    
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.