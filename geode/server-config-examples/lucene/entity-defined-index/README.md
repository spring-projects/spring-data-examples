# Entity Defined Index Example

In this example the [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) server creates a Lucene index for the `Customers` region on `lastName` and uses a `LuceneTemplate` to query the indexed data.

To run the example simply run the tests located under lucene/entity-defined-index/src/test in your IDE.

## Running the example

The example is broken up into multiple steps:
1. Insert (Put) 300 Customer entries into the `Customers` region using the repositories `save` method.
2. Use the `LuceneTemplate` to query for and print all customers with last names beginning with "D".

Your test output should contain output similar to the following:

    Inserting 300 customers
    Completed creating customers 
    Customers with last names beginning with 'D':
    Customer(id=2, emailAddress=EmailAddress(value=corine.ullrich@yahoo.com), firstName=Audra, lastName=D'Amore)
    Customer(id=206, emailAddress=EmailAddress(value=stuart.walter@gmail.com), firstName=Adelia, lastName=Dach)
    Customer(id=204, emailAddress=EmailAddress(value=jammie.schmeler@hotmail.com), firstName=Vidal, lastName=DuBuque)
    Customer(id=75, emailAddress=EmailAddress(value=rebekah.dickinson@yahoo.com), firstName=Kaylin, lastName=DuBuque)
    Customer(id=73, emailAddress=EmailAddress(value=layla.wilderman@yahoo.com), firstName=Ines, lastName=Dibbert)
    Customer(id=173, emailAddress=EmailAddress(value=sincere.turner@yahoo.com), firstName=Mohamed, lastName=Dare)
    Customer(id=278, emailAddress=EmailAddress(value=maybelle.kling@hotmail.com), firstName=Rigoberto, lastName=Dickens)
    Customer(id=158, emailAddress=EmailAddress(value=coleman.harber@gmail.com), firstName=Madeline, lastName=Dooley)
    Customer(id=31, emailAddress=EmailAddress(value=christiana.windler@yahoo.com), firstName=Damaris, lastName=Dickens)
    Customer(id=138, emailAddress=EmailAddress(value=kole.kerluke@yahoo.com), firstName=Rhianna, lastName=Donnelly)
    Customer(id=19, emailAddress=EmailAddress(value=elise.macejkovic@hotmail.com), firstName=Jamir, lastName=Doyle)
    
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.