# Spring Data - Multi-store example

This sample shows a project working with multiple Spring Data modules and how the repository auto-detection has become more strict with the Evans release train.

If you run `ApplicationConfigurationTest` you should see the following output:

```
… DEBUG … - Multiple Spring Data modules found, entering strict repository configuration mode!
… 
… DEBUG … - Spring Data JPA - Could not safely identify store assignment for repository candidate interface example.springdata.multistore.shop.OrderRepository.
… DEBUG … - Spring Data JPA - Registering repository: customerRepository - Interface: example.springdata.multistore.customer.CustomerRepository - Factory: org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean
… DEBUG … - Multiple Spring Data modules found, entering strict repository configuration mode!
…
… DEBUG … - Spring Data MongoDB - Could not safely identify store assignment for repository candidate interface example.springdata.multistore.customer.CustomerRepository.
… DEBUG … - Spring Data MongoDB - Registering repository: orderRepository - Interface: example.springdata.multistore.shop.OrderRepository - Factory: org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean
```

As you can see, Spring Data detects the fact that the application runs on multiple Spring Data modules. This triggers the strict configuration mode in which only repository interfaces will be detected that can be uniquely assigned to the module currently scanned. By default this assignment is detected by inspecting the managed domain type for store specific annotations (e.g. `@Entity` for JPA or `@Document` for MongoDB).  
