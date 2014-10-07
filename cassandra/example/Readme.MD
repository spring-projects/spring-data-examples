# Spring Data Cassandra Example Application

## Preparation

### Install Cassandra
Before we can start we have to install Cassandra, e.g. via brew on Max OS.
```
brew install cassandra
pip install cassandra-driver
brew install cassandra-driver

More details can be found here: https://wiki.apache.org/cassandra/GettingStarted
```

### Start Cassandra
```
/usr/local/bin/cassandra -f 
```

### Start a Cassandra shell cqlsh
```
/usr/local/bin/cqlsh
```

### Setup Keyspace and Tables

```
CREATE KEYSPACE example
WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

USE example;

CREATE TABLE users (
  user_id int PRIMARY KEY,
  uname text,
  fname text,
  lname text
);
```

That should be enough to get you started.
Now you can simply type ```mvn clean install``` to run the example.