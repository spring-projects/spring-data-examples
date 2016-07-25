# Spring Data Cassandra Example Application

## Preparation

### Install Cassandra
Before we can start we have to install Cassandra, e.g. via brew on Max OS.
```
brew install cassandra

More details can be found here: https://wiki.apache.org/cassandra/GettingStarted
```

### Start Cassandra
```
/usr/local/bin/cassandra -f 
```

That should be enough to get you started.
Now you can simply type ```mvn clean install``` to run the example.
