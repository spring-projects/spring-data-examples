# Spring Data Examples

[![Build Status](https://travis-ci.org/spring-projects/spring-data-examples.svg?branch=main)](https://travis-ci.org/spring-projects/spring-data-examples)

This repository contains example projects for the different Spring Data modules to showcase the API and how to use the features provided by the modules.

We have separate folders for the samples of individual modules:

## Spring Data for Apache Cassandra

* `example` - Shows core Spring Data support for Apache Cassandra.
* `kotlin` - Example for using Cassandra with Kotlin.
* `reactive` - Example project to show reactive template and repository support.

## Spring Data Elasticsearch

* `example` - Example how to use basic text search, geo-spatial search and facets. It uses
  the High Level REST Client backing template and repository.
* `reactive` - Example how to use reactive client, template and repository features.

Local Elasticsearch instance must be running to run the tests.

## Spring Data for Apache Geode

* `events` - In this example the test will make use of event handlers and async event
  queue to handle events.
* `expiration-eviction` - In these examples the server is configured to delete entries
  after a certain idle period or after a Time-To-Live period (expiration0 or remove data
  from memory when certain thresholds are reached (eviction).
* `function-invocation` - In this example the server will have 3 functions registered. The
  client will invoke each of the functions.
* `queries` - In this example a client will query the data in various ways using OQl,
  continuous queries, and Apache Lucene indexes.
* `security` - In this example the servers and clients are set up with security (
  username/password) authentication using Geode Security and Apache Shiro.
* `storage` - In this example the server is configured to store data off of hte JVM heap
  using the `@EnableOffHeap` annotation and to compress region data using
  SnappyCompressor`.
* `transactions` - In this example the client will perform operations within a
  transaction. First, it will do a successful transaction where entries are saved to the
  server, and then a failed transaction where all changes are reverted.
* `wan` - In these example two servers are deployed. One server populates itself with data, and the other server gets populated with that data via WAN replication.

## Spring Data JDBC

* `basic` - Basic usage of Spring Data JDBC.
* `immutables` - Showing Spring Data JDBC usage
  with [Immutables](https://immutables.github.io/)

## Spring Data JPA

* `eclipselink` - Sample project to show how to use Spring Data JPA with Spring Boot and [Eclipselink](https://www.eclipse.org/eclipselink/).
* `example` - Probably the project you want to have a look at first. Contains a variety of sample packages, showcasing the different levels at which you can use Spring Data JPA. Have a look at the `simple` package for the most basic setup.
* `interceptors` - Example of how to enrich the repositories with AOP.
* `jpa21` - Shows support for JPA 2.1 specific features (stored procedures support).
* `multiple-datasources` - Examples of how to use Spring Data JPA with multiple `DataSource`s.
* `query-by-example` - Example project showing usage of Query by Example with Spring Data JPA.
* `security` - Example of how to integrate Spring Data JPA Repositories with Spring Security.
* `showcase` - Refactoring show case of how to improve a plain-JPA-based persistence layer by using Spring Data JPA (read: removing close to all of the implementation code). Follow the `demo.txt` file for detailed instructions.
* `vavr` - Shows the support of [Vavr](https://www.vavr.io) collection types as return types for query methods.

## Spring Data LDAP

* `example` - Sample for Spring Data repositories to access an LDAP store.

## Spring Data MongoDB

* `aggregation` - Example project to showcase the MongoDB aggregation framework support.
* `example` - Example project for general repository functionality (including geo-spatial functionality), Querydsl integration and advanced topics.
* `fluent-api` - Example project to show the new fluent API (`MongoTemplate`-alternative) to interact with MongoDB.
* `geo-json` - Example project showing usage of [GeoJSON](http://geojson.org) with MongoDB.
* `gridfs` - Example project showing usage of gridFS with MongoDB.
* `jmolecules` - Example of Spring Data MongoDB working with a jMolecules based domain model.
* `kotlin` - Example for using [Kotlin](https://kotlinlang.org/) with MongoDB.
* `query-by-example` - Example project showing usage of Query by Example with MongoDB.
* `querydsl` - Example project showing imperative and reactive [Querydsl](https://github.com/querydsl/querydsl) support for MongoDB.
* `reactive` - Example project to show reactive template and repository support.
* `repository-metrics` - Example project to show how to collect repository method invocation metrics.
* `security` - Example project showing usage of Spring Security with MongoDB.
* `text-search` - Example project showing usage of MongoDB text search feature.
* `transactions` - Example project for imperative and reactive MongoDB 4.0 transaction support.

## Spring Data Neo4j

* `example` - Example to show basic node and relationship entities and repository usage.

## Spring Data R2DBC

* `example` - Basic usage of Spring Data R2DBC.

## Spring Data Redis

* `cluster` - Example for Redis Cluster support.
* `example` - Example for basic Spring Data Redis setup.
* `reactive` - Example project to show reactive template support.
* `repositories` - Example demonstrating Spring Data repository abstraction on top of Redis.
* `sentinel` - Example for Redis Sentinel support.
* `streams` - Example for [Redis Streams](https://redis.io/topics/streams-intro) support.

Local Redis instances must be running to run the tests.

## Spring Data REST

* `headers` - A sample showing the population of HTTP headers and the usage of them to perform conditional `GET` requests.
* `multi-store` - A sample REST web-service based on both Spring Data JPA and Spring Data MongoDB.
* `projections` - A sample REST web-service showing how to use projections.
* `security` - A sample REST web-service secured using Spring Security.
* `starbucks` - A sample REST web-service built with Spring Data REST and MongoDB.
* `uri-customizations` - Example project to show URI customization capabilities.

## Spring Data web support

* `projections` - Example for Spring Data web support for JSONPath and XPath expressions on projection interfaces.
* `querydsl` - Example for Spring Data Querydsl web integration (creating a `Predicate` from web requests).
* `web` - Example for Spring Data web integration (binding `Pageable` instances to Spring MVC controller methods, using interfaces to bind Spring MVC request payloads).

## Miscellaneous

* `bom` - Example project how to use the Spring Data release train bom in non-Spring-Boot
  scenarios.
* `map` - Example project to show how to use `Map`-backed repositories.
* `multi-store` - Example project to use both Spring Data MongoDB and Spring Data JPA in
  one project.

## Note

* The example projects make use of the [Lombok](https://projectlombok.org/) plugin. To get
  proper code navigation in your IDE, you must install it separately. Lombok is available
  in the IntelliJ plugins repository and as
  a [download](https://projectlombok.org/download) for Eclipse-based IDEs.
* The code makes use of Java 16 language features therefore you need Java 16 or newer to
  run and compile the examples.
* Most store modules examples start their database via Testcontainers or as
  embedded/in-memory server unless stated otherwise.
