# Expiration Examples

In these examples the [Pivotal GemFire](https://pivotal.io/pivotal-gemfire) / [Apache Geode](http://geode.apache.org/) server is configured to delete entries after a certain idle period or after a Time-To-Live period.

There are three examples located under expiration in the project structure. All three examples accomplish the same thing, but they do it in different ways.

## Running the example

Once you've found the example you wish to run, simply run one of the tests from your IDE. All three tests should produce the same output.

NOTE: Inorder to see output, you must change the loglevel from "error" to "info" in the `logback.xml` file located under src/test/resources.