# Expiration and Eviction Example

In this example we will show you ways to automatically remove data from your region. There are two ways to do this; Expiration and Eviction.

1. Expiration removes data after it has existed for a certain amount of time or after it has been unused for a certain amount of time. There are multiple ways to configure Expiration that will be shown in this example.
    1. Configure an eviction policy on the region using the `@EnableExpiration` and `@ExpirationPolicy` annotations.
    2. Configure a custom eviction policy to extending `CustomExpiry`.
    3. Entity defined expiration using the `@IdleTimeoutExpiration` and `@TimeToLiveExpiration` annotations.
    
Entity defined expiration has its own test class because it has a lower priority and is trumped by the expiration policy defined on the `@EnableExpiration` annotation. The polices defined on the `@EnableExpiration` annotation can be found in ExpirationPolicyConfig.

NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.