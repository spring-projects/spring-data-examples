# Spring Data REST - Starbucks example

This sample app exposes 10843 Starbucks coffee shops via a RESTful API that allows to access the stores in a hypermedia based way and exposes a resource to execute geo-location search for coffee shops.

## Quickstart

1. Install MongoDB (http://www.mongodb.org/downloads, unzip, run `bin/mongod --dbpath=data`)
2. Build and run the app (`mvn spring-boot:run`)
3. Access the root resource (`curl http://localhost:8080`) and traverse hyperlinks.
4. Or access the location search directly (e.g. `localhost:8080/stores/search/findByAddressLocationNear?location=40.740337,-73.995146&distance=0.5miles`)

## API exploration

The module uses the HAL Browser module of Spring Data REST which serves a UI to explore the resources exposed. Point your browser to `http://localhost:8080` to see it.

## Technologies used

- Spring Data REST & Spring Data MongoDB
- MongoDB
- Spring Batch (to read the CSV file containing the store data and pipe it into MongoDB)
- Spring Boot