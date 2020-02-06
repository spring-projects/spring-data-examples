# Server Config Examples

The examples are focused on the configuration of the [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) server and server components.

The examples consist of a server or servers. Some examples (the WAN examples) do contain simple clients for the purpose of verification. Most tests do not involve a client at all.

All examples are configured as a `look-aside cache`, where the client stores no data locally. If you want to configure a `near cache`, where the client stores a subset of the data locally, simply change the region shortcut from `PROXY` to `CACHING_PROXY`.

## Server Configuration and Deployment

To run the examples simply run the test, which will start the server and perform operations. The logging level of the examples is set to "error", so there will be no output. To see output, simply find the `logback.xml` file located in src/test/resources and set the loglevel to "info".