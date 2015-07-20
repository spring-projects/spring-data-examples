# Spring Data Web - QueryDSL example

This example shows some of the Spring Data QueryDSL integration features with Spring MVC.

## Quickstart

1. Install MongoDB (http://www.mongodb.org/downloads, unzip, run `mkdir data`, run `bin/mongod --dbpath=data`)
2. Build and run the app (`mvn spring-boot:run`)
3. Access the root resource (`curl http://localhost:8080/api`) and traverse hyperlinks.
4. Or access app directly via its UI (`http://localhost:8080/`).

## Technologies used

- Spring Data REST & QueryDSL & Spring Data MongoDB
- MongoDB
- Spring Batch (to read the CSV file containing the store data and pipe it into MongoDB)
- Spring Boot
