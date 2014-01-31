# Spring Data Examples

This repository contains example projects for the different Spring Data modules to showcase the API and how to use the features provided by the modules.

We have separate folders for the samples of individual modules:

## Spring Data JPA

* `spring-data-jpa-example` - Probably the project you want to have a look at first. Contains a variety of sample packages, showcasing the different levels at which you can use Spring Data JPA. Have a look at the `simple` package for the most basic setup.
* `spring-data-jpa-java8-auditing` - Example of how to use Spring Data JPA auditing with Java 8 date time types. Note, this project requires to be build with JDK 8.
* `spring-data-jpa-showcase` - Refactoring show case of how to improve a plain-JPA-based persistence layer by using Spring Data JPA (read: removing close to all of the implementation code). Follow the `demo.txt` file for detailed instructions.
* `spring-data-jpa-interceptors` - Example of how to enrich the repositories with AOP.

## Spring Data MongoDB

* `spring-data-mongodb-example` - Example project for general repository functionality as well as aggregation framework support.
