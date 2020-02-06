# Entity Defined Regions Example

In this example a [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) client will be configured to perform the basic CRUD operations. Regions are defined only on the client which will use `@EnableEntityDefinedRegions` to configure regions on the server.

To run the example simply run the tests located under entity-defined-regions/src/test in your IDE.

The client is configured to connect to the server on `localhost` port `40404`. There is no need to start the server separately, as that is taken care of by the test.

## Running the example

The example is broken up into multiple steps:
1. Insert (Put) three Customer entries into the `Customers` region using the repositories `save` method.
2. Print out the size of the local region in the cache
3. Print out the size of the region on the server
4. Update the Customer for id=2. Recording the before and after Customer detail
5. Remove (delete) the Customer for id=3 

Your output from the test `customerRepositoryWasAutoConfiguredCorrectly` should look similar to the following:

    Inserting 3 entries for keys: 1, 2, 3
    Entries on Client: 0
    Entries on Server: 3
    	 Entry: 
     		 Customer(id=1, emailAddress=EmailAddress(value=2@2.com), firstName=John, lastName=Smith)
    	 Entry: 
     		 Customer(id=2, emailAddress=EmailAddress(value=3@3.com), firstName=Frank, lastName=Lamport)
    	 Entry: 
     		 Customer(id=3, emailAddress=EmailAddress(value=5@5.com), firstName=Jude, lastName=Simmons)
    Updating entry for key: 2
    Entry Before: Customer(id=2, emailAddress=EmailAddress(value=3@3.com), firstName=Frank, lastName=Lamport)
    Entry After: Customer(id=2, emailAddress=EmailAddress(value=4@4.com), firstName=Sam, lastName=Spacey)
    Removing entry for key: 3
    Entries:
    	 Entry: 
     		 Customer(id=1, emailAddress=EmailAddress(value=2@2.com), firstName=John, lastName=Smith)
    	 Entry: 
     		 Customer(id=2, emailAddress=EmailAddress(value=4@4.com), firstName=Sam, lastName=Spacey)
 
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.