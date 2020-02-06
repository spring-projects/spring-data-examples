# Cascading Functions Example

In this example the [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) client calls a function and feeds the result into another function.

To run the example simply run the tests located under basic-operations/src/test in your IDE.

The client is configured to connect to the server on `localhost` port `40404`. There is no need to start the server separately, as that is taken care of by the test.

## Running the example

The example is broken up into multiple steps:
1. Insert (Put) 10000 Customer entries into the `Customers` region using the repositories `save` method.
2. Insert (Put) three Product entries into the `Products` region using the repositories `save` method.
3. Insert (Put) ten Order entries into the `Orders` region using the repositories `save` method.
4. Invoke a function to retrieve all Customer entries from the `Cusomers` region and print the number of results.
5. Invoke a function to find all orders for customers using the list returned from the previous function and print the list of returned orders.

Your output from the test `testMethod` should look similar to the following:

    [FORK] - I'm executing function: "listAllCustomers" size= 10000
    Number of customers retrieved from servers: 10000
    [FORK] - I'm executing function: "findOrdersForCustomer" size= 10
    [FORK] - Return Value: [Order(id=1, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    [FORK] - 	LineItems:[Purchased 3 of Product Apple iPod at 99.99 for total of 299.97, Purchased 2 of Product Apple macBook at 899.99 for total of 1799.98, Purchased 3 of Product Apple iPod at 99.99 for total of 299.97], Order(id=2, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    [FORK] - 	LineItems:[Purchased 3 of Product Apple iPod at 99.99 for total of 299.97, Purchased 2 of Product Apple iPod at 99.99 for total of 199.98, Purchased 2 of Product Apple macBook at 899.99 for total of 1799.98], Order(id=3, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    [FORK] - 	LineItems:[Purchased 1 of Product Apple iPod at 99.99 for total of 99.99], Order(id=4, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    [FORK] - 	LineItems:[Purchased 1 of Product Apple macBook at 899.99 for total of 899.99], Order(id=5, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    [FORK] - 	LineItems:[Purchased 3 of Product Apple iPad at 499.99 for total of 1499.97, Purchased 2 of Product Apple iPod at 99.99 for total of 199.98], Order(id=6, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    [FORK] - 	LineItems:[Purchased 1 of Product Apple iPad at 499.99 for total of 499.99, Purchased 1 of Product Apple iPad at 499.99 for total of 499.99, Purchased 1 of Product Apple macBook at 899.99 for total of 899.99], Order(id=7, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    [FORK] - 	LineItems:[Purchased 2 of Product Apple iPad at 499.99 for total of 999.98, Purchased 1 of Product Apple macBook at 899.99 for total of 899.99, Purchased 2 of Product Apple iPod at 99.99 for total of 199.98], Order(id=8, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    [FORK] - 	LineItems:[Purchased 3 of Product Apple iPod at 99.99 for total of 299.97, Purchased 1 of Product Apple iPad at 499.99 for total of 499.99], Order(id=9, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    [FORK] - 	LineItems:[Purchased 2 of Product Apple macBook at 899.99 for total of 1799.98, Purchased 3 of Product Apple iPod at 99.99 for total of 299.97], Order(id=10, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    [FORK] - 	LineItems:[Purchased 3 of Product Apple iPad at 499.99 for total of 1499.97, Purchased 3 of Product Apple macBook at 899.99 for total of 2699.97]]
    [Order(id=1, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    	LineItems:[Purchased 3 of Product Apple iPod at 99.99 for total of 299.97, Purchased 2 of Product Apple macBook at 899.99 for total of 1799.98, Purchased 3 of Product Apple iPod at 99.99 for total of 299.97], Order(id=2, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    	LineItems:[Purchased 3 of Product Apple iPod at 99.99 for total of 299.97, Purchased 2 of Product Apple iPod at 99.99 for total of 199.98, Purchased 2 of Product Apple macBook at 899.99 for total of 1799.98], Order(id=3, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    	LineItems:[Purchased 1 of Product Apple iPod at 99.99 for total of 99.99], Order(id=4, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    	LineItems:[Purchased 1 of Product Apple macBook at 899.99 for total of 899.99], Order(id=5, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    	LineItems:[Purchased 3 of Product Apple iPad at 499.99 for total of 1499.97, Purchased 2 of Product Apple iPod at 99.99 for total of 199.98], Order(id=6, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    	LineItems:[Purchased 1 of Product Apple iPad at 499.99 for total of 499.99, Purchased 1 of Product Apple iPad at 499.99 for total of 499.99, Purchased 1 of Product Apple macBook at 899.99 for total of 899.99], Order(id=7, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    	LineItems:[Purchased 2 of Product Apple iPad at 499.99 for total of 999.98, Purchased 1 of Product Apple macBook at 899.99 for total of 899.99, Purchased 2 of Product Apple iPod at 99.99 for total of 199.98], Order(id=8, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    	LineItems:[Purchased 3 of Product Apple iPod at 99.99 for total of 299.97, Purchased 1 of Product Apple iPad at 499.99 for total of 499.99], Order(id=9, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    	LineItems:[Purchased 2 of Product Apple macBook at 899.99 for total of 1799.98, Purchased 3 of Product Apple iPod at 99.99 for total of 299.97], Order(id=10, customerId=10, billingAddress=Address(street=it, city=doesn't, country=matter), shippingAddress=Address(street=it, city=doesn't, country=matter)) 
    	LineItems:[Purchased 3 of Product Apple iPad at 499.99 for total of 1499.97, Purchased 3 of Product Apple macBook at 899.99 for total of 2699.97]]