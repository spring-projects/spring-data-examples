# Spring Data Examples

This repository contains example projects for the different Spring Data modules to showcase the API and how to use the features provided by the modules.

We have separate folders for the samples of individual modules:

## Spring Data JPA

* `example` - Probably the project you want to have a look at first. Contains a variety of sample packages, showcasing the different levels at which you can use Spring Data JPA. Have a look at the `simple` package for the most basic setup.
* `java8` - Example of how to use Spring Data JPA auditing with Java 8 date time types as well as the usage of `Optional` as return type for repository methods. Note, this project requires to be build with JDK 8.
* `showcase` - Refactoring show case of how to improve a plain-JPA-based persistence layer by using Spring Data JPA (read: removing close to all of the implementation code). Follow the `demo.txt` file for detailed instructions.
* `interceptors` - Example of how to enrich the repositories with AOP.
* `security` - Example of how to integrate Spring Data JPA Repositories with Spring Security.

## Spring Data MongoDB

* `geo-spatial` - Example project for general repository functionality (including geo-spatial functionality) and Querydsl integration
* `aggregation` - Example project to showcase the MongoDB aggregation framework support.

## Spring Data REST

* `starbucks` - A sample REST web-service built with Spring Data REST and MongoDB.
* `multi-store` - A sample REST web-service based on both Spring Data JPA and Spring Data MongoDB.

## Miscellaneous

* `multi-store` - Example project to use both Spring Data MongoDB and Spring Data JPA in one project.