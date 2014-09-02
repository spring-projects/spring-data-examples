# Spring Data Solr - Managed Schema Examples

In order to run this example a 4.7+ [Solr Server](http://lucene.apache.org/solr/downloads.html) and [Maven](http://maven.apache.org/download.cgi) are required.

### Running Solr
```emacs
:solr> cd example
:example> java -Dsolr.solr.home=example-schemaless/solr -jar start.jar
```

Access via [localhost:8983/solr/](http://localhost:8983/solr/#/collection1).
Fields available at [../schema/fields](http://localhost:8983/solr/collection1/schema/fields)