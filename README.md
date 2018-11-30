# Spring Data Examples

[![Build Status](https://travis-ci.org/spring-projects/spring-data-examples.svg?branch=issue%2F%2313)](https://travis-ci.org/spring-projects/spring-data-examples)

This repository contains example projects for the different Spring Data modules to showcase the API and how to use the features provided by the modules.

We have separate folders for the samples of individual modules:

## Spring Data JPA

* `eclipselink` - Sample project to show how to use Spring Data JPA with Spring Boot and [Eclipselink](https://www.eclipse.org/eclipselink/).
* `example` - Probably the project you want to have a look at first. Contains a variety of sample packages, showcasing the different levels at which you can use Spring Data JPA. Have a look at the `simple` package for the most basic setup.
* `interceptors` - Example of how to enrich the repositories with AOP.
* `java8` - Example of how to use Spring Data JPA auditing with Java 8 date time types as well as the usage of `Optional` as return type for repository methods. Note, this project requires to be build with JDK 8.
* `jpa21` - Shows support for JPA 2.1 specific features (stored procedures support).
* `multiple-datasources` - Examples of how to use Spring Data JPA with multiple `DataSource`s.
* `query-by-example` - Example project showing usage of Query by Example with Spring Data JPA.
* `security` - Example of how to integrate Spring Data JPA Repositories with Spring Security.
* `showcase` - Refactoring show case of how to improve a plain-JPA-based persistence layer by using Spring Data JPA (read: removing close to all of the implementation code). Follow the `demo.txt` file for detailed instructions.
* `vavr` - Shows the support of [Vavr](https://www.vavr.io) collection types as return types for query methods.

## Spring Data MongoDB

* `aggregation` - Example project to showcase the MongoDB aggregation framework support.
* `example` - Example project for general repository functionality (including geo-spatial functionality), Querydsl integration and advanced topics.
* `fluent-api` - Example project to show the new fluent API (`MongoTemplate`-alternative) to interact with MongoDB.
* `geo-json` - Example project showing usage of [GeoJSON](http://geojson.org) with MongoDB.
* `gridfs` - Example project showing usage of gridFS with MongoDB.
* `java8` - Example of how to use Spring Data MongoDB with Java 8 date time types as well as the usage of `Optional` as return type for repository methods. Note, this project requires to be build with JDK 8.
* `kotlin` - Example for using Cassandra with MongoDB.
* `query-by-example` - Example project showing usage of Query by Example with MongoDB.
* `reactive` - Example project to show reactive template and repository support.
* `security` - Example project showing usage of Spring Security with MongoDB.
* `text-search` - Example project showing usage of MongoDB text search feature.
* `transactions` - Example project for synchronous and reactive MongoDB 4.0 transaction support.

## Spring Data REST

* `headers` - A sample showing the population of HTTP headers and the usage of them to perform conditional `GET` requests.
* `multi-store` - A sample REST web-service based on both Spring Data JPA and Spring Data MongoDB.
* `projections` - A sample REST web-service showing how to use projections.
* `security` - A sample REST web-service secured using Spring Security.
* `starbucks` - A sample REST web-service built with Spring Data REST and MongoDB.
* `uri-customizations` - Example project to show URI customization capabilities.

## Spring Data Redis

* `cluster` - Example for Redis Cluster support.
* `example` - Example for basic Spring Data Redis setup.
* `reactive` - Example project to show reactive template support.
* `repositories` - Example demonstrating Spring Data repository abstraction on top of Redis.
* `sentinel` - Example for Redis Sentinel support.

## Spring Data for Apache Solr

* `example` - Example project for Spring Data repositories for Apache Solr.
* `managed-schema` - Example project to show managed schema integration.

## Spring Data Elasticsearch

* `example` - Example how to use basic text search, geo-spatial search and facets.

## Spring Data Neo4j

* `example` - Example to show basic node and relationship entities and repository usage.

## Spring Data web support

* `projections` - Example for Spring Data web support for JSONPath and XPath expressions on projection interfaces.
* `querydsl` - Example for Spring Data Querydsl web integration (creating a `Predicate` from web requests).
* `web` - Example for Spring Data web integration (binding `Pageable` instances to Spring MVC controller methods, using interfaces to bind Spring MVC request payloads).

## Spring Data for Apache Cassandra

* `example` - Shows core Spring Data support for Apache Cassandra.
* `java8` - Java 8 specific functionality like the support for JSR-310 types in object mapping.
* `kotlin` - Example for using Cassandra with Kotlin.
* `reactive` - Example project to show reactive template and repository support.

## Spring Data LDAP

* `example` - Sample for Spring Data repositories to access an LDAP store.

## Spring Data JDBC

* `basic` - Basic usage of Spring Data JDBC.

## Spring Data R2DBC

* `example` - Basic usage of Spring Data R2DBC.

## Miscellaneous

* `bom` - Example project how to use the Spring Data release train bom in non-Spring-Boot scenarios.
* `map` - Example project to show how to use `Map`-backed repositories.
* `multi-store` - Example project to use both Spring Data MongoDB and Spring Data JPA in one project.
