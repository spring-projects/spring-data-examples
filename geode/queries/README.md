# Queries Example

In this example we will demonstrate different kinds of queries. The kinds of queries we will demonstrate are listed below.
1. OQL Queries - OQL is similar to SQl and can be used to query regions like SQl queries tables in a relational database.
2. Continuous Queries - Continuous queries are special OQl queries that run continuously and are updated when th returned result set changes (like then more data is added/removed on the region)
3. Lucene Queries - Lucene Queries allow you ot query data that has been indexed with Apache Geode's Apache Lucene support.
    
NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.