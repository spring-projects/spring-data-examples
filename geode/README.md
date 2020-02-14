Spring Data For GemFire and Apache Geode Examples
=========================================================

This project provides a number of examples to get you started using Spring Data for Apache Geode or Pivotal GemFire. These examples are designed to work with [Spring Data for Pivotal GemFire](http://projects.spring.io/spring-data-gemfire) 2.0.9-RELEASE or higher and are organized into the following sub projects:

It is important to note that all examples will follow the prescribed Maven directory structure.

Examples:

* **events** - In this example the test will make use of event handlers and async event queue to handle events.
* **expiration-eviction** - In these examples the server is configured to delete entries after a certain idle period or after a Time-To-Live period (expiration0 or remove data from memory when certain thresholds are reached (eviction).
* **function-invocation** - In this example the server will have 3 functions registered. The client will invoke each of the functions.
* **queries** - In this example a client will query the data in various ways using OQl, continuous queries, and Apache Lucene indexes.
* **security** - In this example the servers and clients are set up with security (username/password) authentication using Geode Security and Apache Shiro.
* **storage** - In this example the server is configured to store data off of hte JVM heap using the `@EnableOffHeap` annotation and to compress region data using SnappyCompressor`.
* **transactions** - In this example the client will perform operations within a transaction. First, it will do a successful transaction where entries are saved to the server, and then a failed transaction where all changes are reverted.
* **wan** - In these example two servers are deployed. One server populates itself with data, and the other server gets populated with that data via WAN replication.

# Running The Examples

Each example has at least one test file located in the test directory. The examples are driven by the tests, so simply run the test either through your IDE or via the commandline. 
The logging level of the examples is set to "error", so there will be no output. To see output, simply find the `logback.xml` file located in src/test/resources and set the loglevel to "info".