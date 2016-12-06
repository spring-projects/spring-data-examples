# Spring Data MongoDB - GridFS example

This project contains an example of [GridFS](https://docs.mongodb.com/v3.4/core/gridfs/) specific features of Spring Data (MongoDB).

## Support for storing a file

Using [GridFsOperations](http://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/gridfs/GridFsOperations.html) to store a file.

```java
InputStream is = ...
gridFsOperations.store(is, "myFile1.txt");
```

## Support for query a file by name

Using [GridFsOperations](http://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/gridfs/GridFsOperations.html) to query a GridFSDBFile by its filename.

```java
GridFSDBFile gridFsFile = gridFsOperations.findOne(query(whereFilename().is("myFile1.txt")));
```

## Support for storing a file with metadata

Using [GridFsOperations](http://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/gridfs/GridFsOperations.html) to store a file with additional metadata..

```java
InputStream is = ...
Customer customerMetaData = new Customer("Hardy", "Lang");
gridFsOperations.store(is, "myCustomerFile.txt", customerMetaData);

```

## Support for query a file by metadata

Using [GridFsOperations](http://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/gridfs/GridFsOperations.html) to query a GridFSDBFile by metadata.

```java
GridFSDBFile gridFsFile = gridFsOperations.findOne(query(whereMetaData("firstName").is("Hardy")));
```
