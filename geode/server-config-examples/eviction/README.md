# Eviction Example

In this example the [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) server is configured to evict entries from memory. Some region will overflow to disk while others will be configured to delete entries that exceed the eviction threshold.

To run the example simply run the tests located under eviction/src/test in your IDE.

## Running the example

The example is broken up into multiple steps:
1. Insert (Put) 300 Customer entries into the `Customers` region using the repositories `save` method.
2. Insert (Put) 300 Product entries into the `Products` region using the repositories `save` method.
3. Insert (Put) 100 Order entries into the `Orders` region using the repositories `save` method.
4. The last 90 Orders are evicted since the threshold is set to 10.
5. Print the number of Orders in the `Orders` region.

Your test output should contain output similar to the following:

    Inserting 300 customers
    Inserting 300 products
    Inserting 100 orders

    There are 10 orders in the Orders region
    
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.