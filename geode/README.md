Spring Data For Pivotal GemFire and Apache Geode Examples
=========================================================

This project provides a number of examples to get you started using Spring Data for Apache Geode or Pivotal GemFire. These examples are designed to work with [Spring Data for Pivotal GemFire](http://projects.spring.io/spring-data-gemfire) 2.0.9-RELEASE or higher and are organized into the following sub projects:

* **Client Server** - These examples pertain to the client-server paradigm. These examples will involve a client connecting to a server to perform operations. 
* **Server Config** - These examples demonstrate the configuration of servers. This includes regions, persistence, wan, subscriptions, functions and indexes.
* **Advanced** - These examples are to show case how higher-level use cases are solved.

It is important to note that all examples will follow the prescribed Maven directory structure.

# Client Server examples
These examples are primarily focused on the client-side configuration and functionality, rather than that of the deployed server. The deployed server will be setup with the minimum required configuration to fulfill the requirements of the examples.

Examples:
* **basic-operations** - In this example the client will perform basic CRUD operations.
* **cluster-defined-regions** - In this example the regions are defined only on the server. The client will use `@EnableClusterConfiguration` to configure regions locally.
* **continuous-queries** - In this example a client puts data into the region on the server, and registers a continuous query on that data.
* **entity-defined-regions** - In this example a server is deployed with no regions defined. The client will use the `@EnableEntityDefinedRegions` to configure regions on the server(s).
* **function-invocation** - In this example the server will have 3 functions registered. The client will invoke each of the functions.
* **oql-queries** - In this example the client will perform OQL queries. This example utilizes both GemFireTemplate and GemFireRepositories to query and implements indexes to increase query performance.
* **security** - In this example the servers and clients are set up with security (username/password) authentication using Geode Security and Apache Shiro.
* **serialization** - In this example the server stores data serialized as a `PdxInstance` instead of using the `Customer` class.
* **transactions** - In this example the client will perform operations within a transaction. First, it will do a successful transaction where entries are saved to the server, and then a failed transaction where all changes are reverted.
 
# Server Config examples
These examples are focused on the configuration of the server and server components.

Examples:
* **async-queues** - In this example the test inserts entries that go through an async event queue on the server which creates OrderProductSummary entries from the data inserted into the other regions.
* **compression** - In this example the server is configured with `SnappyCompressor` to store region entries in a compressed format.
* **event-handlers** - In this example the server is populated with data, which triggers events in a cache listener, cache writer, and cache loader.
* **eviction** - In this example the server is configured to evict entries from memory. Some region will overflow to disk while others will be configured to delete entries that exceed the eviction threshold.
* **expiration** - In these examples the server is configured to delete entries after a certain idle period or after a Time-To-Live period.
    * **cache-defined-expiration** - In this example expiration is configured on the server using the `@EnableExpiration` annotation to define expiration policy.
    * **custom-expiration** - In this example expiration is configured on the server by an implementation of the `CustomExpiry` interface during region creation.
    * **entity-defined-expiration** - In this example expiration is configured on the server by the domain object using the `@IdleTimeoutExpiration` and `@TimeToLiveExpiration` annotations.
* **lucene** - In these examples the server is configured to use Apache Lucene.
    * **entity-defined-index** - In this example the server creates a Lucene index for the `Customers` region on `lastName` and uses a `LuceneTemplate` to query the indexed data.
* **offheap** - In this example the server is configured to store data off of hte JVM heap using the `@EnableOffHeap` annotation.
* **region-config** - In this example the server makes use of `PartitionAttributes` to set the number of buckets and redundant copies.
* **wan** - In these example two servers are deployed. One server populates itself with data and the other server gets populated with that data via WAN replication.
    * **wan-event-filters** - In this example the `GatewaySender` is configured with  a `GatewayEventFilter` which only lets entries with even key values be replicated to the other server.
    * **wan-substitution-filter** - In this example the `GatewaySender` is configured with  a `GatewayEventSubstitutionFilter` which replaces the value of `lastName` with the first initial while replicating entries to the other server.
    * **wan-transport-filters** - In this example the `GatewaySender` is configured with  a `GatewayTransportFilter` which performs checking on the stream used fr replicating entries between servers.

# Advanced
These examples will show case higher-level use cases and how Spring Data for Pivotal GemFire simplifies the implementation of the use case.

Examples:
* **cascading-functions** - In this example the client calls a function and feeds the result into another function.

# Running The Examples

Each example has at least one test file located in the test directory. The examples are driven by the tests, so simply run the test either through your IDE or via the commandline. 
The logging level of the examples is set to "error", so there will be no output. To see output, simply find the `logback.xml` file located in src/test/resources and set the loglevel to "info".