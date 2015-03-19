# Spring Data JPA - Multiple datasources example

This project contains an example of how to set up Spring Data to work with two independent databases. Note, that this example is not about JTA transactions spanning multiple databases but rather just interacting with separate databases through a cleanly separated domain model and Spring Data repositories.

## Interesting points to look at

Two connect to two databases we need to manually configure a `DataSource`, `EntityManagerFactory` and `JpaTransactionManager`. This is done in `CustomerConfig` and `OrderConfig`. `DataInitializer` then simulates a Spring component interacting with those databases in separate methods. `Application.init()` orchestrates calls to those methods.
