# Async Queues Example

In this example the test inserts entries that go through an async event queue on the [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) server which creates OrderProductSummary entries from the data inserted into the other regions.

To run the example simply run the tests located under async-queues/src/test in your IDE.

## Running the example

The example is broken up into multiple steps:
1. Insert (Put) 300 Customer entries into the `Customers` region using the repositories `save` method.
2. Insert (Put) 3 Product entries into the `Products` region using the repositories `save` method.
3. Insert (Put) 10 Order entries into the `Orders` region using the repositories `save` method.
4. An async queue listener creates and inserts OrderProductSummary entries.
5. Print order product summaries

Your test output should contain output similar to the following:

    Completed creating orders 
    orderProductSummary = OrderProductSummary(summaryKey=OrderProductSummaryKey(productId=3, timebucketStart=1567794309000), summaryAmount=2270674.77)
    orderProductSummary = OrderProductSummary(summaryKey=OrderProductSummaryKey(productId=3, timebucketStart=1567794310000), summaryAmount=3023966.40)
    
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.