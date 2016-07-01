# Spring Data Web - Projection example

This example shows how to use projection interfaces in combination with JSON Path or XPath expressions to bind payload data to an object instance.

The most interesting bit is the following projection interface declared in `UserController`:

```java
@ProjectedPayload
public interface UserPayload {

	@XBRead("//firstname")
	@JsonPath("$..firstname")
	String getFirstname();

	@XBRead("//lastname")
	@JsonPath("$..lastname")
	String getLastname();
}
```

This type is used in `UserController.index(…)` and basically combines two modes of operation.

## Binding request data via JSON Path expression

The `@JsonPath` annotations bind the values obtained by evaluating the expressions from the request. The sample is using a recursive property lookup for `firstname` and `lastname`. Using those expressions allows the payload thats received to slightly changed and the code dealing with not having to be changed.

As an example using that on the server side can be found in `UserControllerIntegrationTests`. The tests sends two different flavors of JSON to simulate a change in behavior of the client and the server can handle both representation formats without the need for a change.

This is also very useful on the client side which is simulated in `UserControllerClientTests` setting up a `RestTemplate` with the newly introduced `HttpMessageConverters` so that the projection interface can be used to access the payload. See how the test case accesses different HTTP resources, that simulate a change in the representation on the server side.

## Support for XPath expressions

The JSON Path support is automatically activated on the server side if the Jayway JSON Path library is on the classpath. Support for XPath is activated if XMLBeam is on the classpath, generally works the same and actually offers quite some more sophisticated features.

