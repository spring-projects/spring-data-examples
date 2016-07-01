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
 
