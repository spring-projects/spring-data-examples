# Spring Data Solr - Managed Schema Examples


In order to run this example either a 6.5+ [Solr Server](http://lucene.apache.org/solr/downloads.html) or a working Docker environment as described by [Testcontainers](https://www.testcontainers.org/usage.html#prerequisites) are required.

If neither is provided all tests will get ignored, essentially disabling the examples. 
If you want to fail the build instead add `-DignoreMissingInfrastructure=false` to the Maven commandline. 

You'll also need and [Maven](http://maven.apache.org/download.cgi).
### Running Solr
```emacs
:solr> ./bin/solr start -e schemaless
```

Access via [localhost:8983/solr/](http://localhost:8983/solr/#/gettingstarted).
Fields available at [../schema/fields](http://localhost:8983/solr/gettingstarted/schema/fields)