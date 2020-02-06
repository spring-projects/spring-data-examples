# Client-Server Examples

The examples are aimed at show casing the the power and versatility that a [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) client possess.

The examples are broken up into client and server code. The test will act as the client and is where most of the work is done.

All examples are configured as a `look-aside cache`, where the client stores no data locally. If you want to configure a `near cache`, where the client stores a subset of the data locally, simply change the region shortcut from `PROXY` to `CACHING_PROXY`.

## Client Configuration and Deployment

To run the examples simply run the test, which will start the server and perform client operations.