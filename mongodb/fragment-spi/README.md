# Spring Data - Fragment SPI Example

This project contains a sample using `spring.factories` to register implementation details for a repository extension for MongoDB Vector Search that lives outside of the project namespace. 

The project is divided into the `atlas-api`, providing the extension, and the `sample` using it.

## atlas-api

The `AtlasRepository` is the base interface containing a `vectorSearch` method that is implemented in `AtlasRepositoryFragment`. The configuration in `src/main/resources/META-INF/spring.factories` makes sure it is picked up by the spring data infrastructure.

The implementation leverages `RepositoryMethodContext` to get hold of method invocation metadata to determine the collection name derived from the repositories domain type `<T>`.
Since providing the metadata needs to be explicitly activated the `AtlasRepositoryFragment` uses the additional marker interface `RepositoryMetadataAccess` enabling the features for repositories extending the `AtlasRepository`.  

## sample

The `MovieRepository` extends the `AtlasRepository` from the api project using a `Movie` type targeting the `movies` collection. No further configuration is needed to use the provided `vectorSearch` within the `MovieRepositoryTests`.

The `Movies` class in `src/main/test` takes care of setting up required test data and indexes.

## Running the sample

The is using a local MongoDB Atlas instance bootstrapped by Testcontainers.
Running the `MovieRepositoryTests` the `test/movies` collection will be populated with about 400 entries from the `mflix.embedded_movies.json` file.
Please be patient while data is loaded into the database and the index created afterwards.
Progress information will be printed to the log.
```log
INFO - com.example.data.mongodb.Movies:  73 - Loading movies mflix.embedded_movies.json
INFO - com.example.data.mongodb.Movies:  90 - Created 420 movies in test.movies
INFO - com.example.data.mongodb.Movies:  65 - creating vector index
INFO - com.example.data.mongodb.Movies:  68 - index 'plot_vector_index' created
```
Once data and index are available search result will be printed:
```log
INFO - ...mongodb.MovieRepositoryTests: 183 - Movie{id='66d6ee0937e07b74aa2939cc', ...
```
