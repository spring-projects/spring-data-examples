# Getting Started

### Running the Sample

* `$> docker run -d --name db -p 8091-8097:8091-8097 -p 11210:11210 -p 11207:11207 -p 18091-18095:18091-18095 -p 18096:18096 -p 18097:18097 couchbase`
* Create new _cluster_ via `http://localhost:8091/`
* Create new [bucket](http://localhost:8091/ui/index.html#/buckets) named _travel-sample_.
* 

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.2/maven-plugin/reference/html/#build-image)
* [Spring Data Couchbase](https://docs.spring.io/spring-boot/docs/2.5.2/reference/htmlsingle/#boot-features-couchbase)

