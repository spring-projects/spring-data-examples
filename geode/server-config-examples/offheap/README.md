# OffHeap Example

In this example the [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) server is configured to store data off of hte JVM heap using the `@EnableOffHeap` annotation.

To run the example simply run the tests located under offheap/src/test in your IDE.

## Running the example

The example is broken up into multiple steps:
1. Insert (Put) 3000 Customer entries into the `Customers` region using the repositories `save` method.
2. Insert (Put) 1000 Product entries into the `Products` region using the repositories `save` method.
3. Print whether the entries in the `Customers` region are stored on or off the heap.
4. Print whether the entries in the `Products` region are stored on or off the heap.

Your test output should contain output similar to the following:

    Entries in 'Customers' region are stored OFF heap
    Entries in 'Products' region are stored OFF heap
    
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.