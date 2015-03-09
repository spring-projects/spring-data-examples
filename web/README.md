# Spring Data - web support example

This example shows some of the Spring Data integration features with Spring MVC.

1. See how we plug into Spring MVC to create `Pageable` instances from request parameters in `UserController.users(…)`.
2. See how interfaces can be used to bind request payloads in `UserController.UserForm`. Spring Data creates a `Map`-backed proxy for you to easily create form-backing objects.