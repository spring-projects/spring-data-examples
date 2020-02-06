# Transactions Example

In this example a [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) client will be configured to perform the basic CRUD operations within a transaction. First, it will do a successful transaction where entries are saved to the server, and then a failed transaction where all changes are reverted.

To run the example simply run the tests located under transactions/src/test in your IDE.

The client is configured to connect to the server on `localhost` port `40404`. There is no need to start the server separately, as that is taken care of by the test.

## Running the example

The example is broken up into multiple steps:
1. Print number of entries on server
3. Insert (Put) five Customer entries into the `Customers` region using the repositories `save` method.
3. Printing out the size of the region on the server
4. Update the Customer for id=2 in a transaction. Recording the before and after Customer detail.
5. Fail a transaction to update the Customer with id=2. Recording the before and after Customer detail.
6. Update the Customer for id=2 with a delay of 1000 ms.
7. Update the Customer for id=2 with a delay of 10 ms.
8. Print customer for id=2

Your output from the test `repositoryWasAutoConfiguredCorrectly` should look similar to the following:

    Number of Entries stored before = 0
    [FORK] - In region [Customers] created key [1] value [Customer(id=1, emailAddress=EmailAddress(value=1@1.com), firstName=John, lastName=Melloncamp)]
    [FORK] - In region [Customers] created key [2] value [Customer(id=2, emailAddress=EmailAddress(value=2@2.com), firstName=Franky, lastName=Hamilton)]
    [FORK] - In region [Customers] created key [3] value [Customer(id=3, emailAddress=EmailAddress(value=3@3.com), firstName=Sebastian, lastName=Horner)]
    [FORK] - In region [Customers] created key [4] value [Customer(id=4, emailAddress=EmailAddress(value=4@4.com), firstName=Chris, lastName=Vettel)]
    [FORK] - In region [Customers] created key [5] value [Customer(id=5, emailAddress=EmailAddress(value=5@5.com), firstName=Kimi, lastName=Rosberg)]
    Number of Entries stored after = 5
    Customer for ID before (transaction commit success) = Customer(id=2, emailAddress=EmailAddress(value=2@2.com), firstName=Franky, lastName=Hamilton)
    [FORK] - In region [Customers] updated key [2] [oldValue [Customer(id=2, emailAddress=EmailAddress(value=2@2.com), firstName=Humpty, lastName=Hamilton)]] new value [Customer(id=2, emailAddress=EmailAddress(value=2@2.com), firstName=Humpty, lastName=Hamilton)]
    Customer for ID after (transaction commit success) = Customer(id=2, emailAddress=EmailAddress(value=2@2.com), firstName=Humpty, lastName=Hamilton)
    [FORK] - [info 2019/09/13 14:39:34.354 PDT <ServerConnection on port 58982 Thread 1> tid=0x4a] In afterRollback for entry(s) [[Customer(id=2, emailAddress=EmailAddress(value=2@2.com), firstName=Numpty, lastName=Hamilton)]]
    [FORK] - 
    Customer for ID after (transaction commit failure) = Customer(id=2, emailAddress=EmailAddress(value=2@2.com), firstName=Humpty, lastName=Hamilton)
    [FORK] - In region [Customers] updated key [2] [oldValue [Customer(id=2, emailAddress=EmailAddress(value=2@2.com), firstName=Numpty, lastName=Hamilton)]] new value [Customer(id=2, emailAddress=EmailAddress(value=2@2.com), firstName=Numpty, lastName=Hamilton)]
    [FORK] - In region [Customers] updated key [2] [oldValue [Customer(id=2, emailAddress=EmailAddress(value=2@2.com), firstName=Frumpy, lastName=Hamilton)]] new value [Customer(id=2, emailAddress=EmailAddress(value=2@2.com), firstName=Frumpy, lastName=Hamilton)]
    Customer for ID after 2 updates with delay = Customer(id=2, emailAddress=EmailAddress(value=2@2.com), firstName=Frumpy, lastName=Hamilton)
    
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.