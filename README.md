# Spring Data Examples

[![Build Status](https://travis-ci.org/spring-projects/spring-data-examples.svg?branch=issue%2F%2313)](https://travis-ci.org/spring-projects/spring-data-examples)

This repository contains example projects for the different Spring Data modules to showcase the API and how to use the features provided by the modules.

We have separate folders for the samples of individual modules:

## Spring Data JPA

* `example` - Probably the project you want to have a look at first. Contains a variety of sample packages, showcasing the different levels at which you can use Spring Data JPA. Have a look at the `simple` package for the most basic setup.
* `java8` - Example of how to use Spring Data JPA auditing with Java 8 date time types as well as the usage of `Optional` as return type for repository methods. Note, this project requires to be build with JDK 8.
* `showcase` - Refactoring show case of how to improve a plain-JPA-based persistence layer by using Spring Data JPA (read: removing close to all of the implementation code). Follow the `demo.txt` file for detailed instructions.
* `interceptors` - Example of how to enrich the repositories with AOP.
* `security` - Example of how to integrate Spring Data JPA Repositories with Spring Security.
* `multiple-datasources` - Examples of how to use Spring Data JPA with multiple `DataSource`s.

## Spring Data MongoDB

* `example` - Example project for general repository functionality (including geo-spatial functionality), Querydsl integration and advanced topics.
* `aggregation` - Example project to showcase the MongoDB aggregation framework support.
* `text-search` - Example project showing usage of MongoDB text search feature.
* `geo-json` - Example project showing usage of [GeoJSON](http://geojson.org) with MongoDB.

## Spring Data REST

* `starbucks` - A sample REST web-service built with Spring Data REST and MongoDB.
* `multi-store` - A sample REST web-service based on both Spring Data JPA and Spring Data MongoDB.
* `projections` - A sample REST web-service showing how to use projections.
* `security` - A sample REST web-service secured using Spring Security.
* `headers` - A sample showing the population of HTTP headers and the usage of them to perform conditional `GET` requests.

## Spring Data Redis

* `example` - Example for basic Spring Data Redis setup.
* `cluster-sentinel` - Example for Redis cluster and Sentinel support.

## Spring Data Elasticsearch

* `example` - Example how to use basic text search, geo-spatial search and facets.

## Spring Data Neo4j

* `example` - Example to show basic node and relationship entities and repository usage.

## Spring Data web support

* `web` - Example for Spring Data web integration (binding `Pageable` instances to Spring MVC controller methods, using interfaces to bind Spring MVCrequest payloads).
* `querydsl` - Example for Spring Data Querydsl web integration (creating a `Predicate` from web requests).

## Miscellaneous

* `bom` - Example project how to use the Spring Data release train bom in non-Spring-Boot scenarios.
* `multi-store` - Example project to use both Spring Data MongoDB and Spring Data JPA in one project.
