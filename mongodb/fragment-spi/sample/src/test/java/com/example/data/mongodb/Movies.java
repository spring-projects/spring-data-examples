/*
 * Copyright 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.data.mongodb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.client.MongoClient;
import com.mongodb.client.model.InsertOneModel;
import lombok.SneakyThrows;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.core.io.ClassPathResource;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Christoph Strobl
 */
class Movies {

    private static final Logger log = LoggerFactory.getLogger(Movies.class);
    public static final String MOVIES_JSON = "mflix.embedded_movies.json";

    private final MongoClient client;
    static final String DATABASE = "test";
    static final String COLLECTION = "movies";

    public Movies(MongoClient client) {
        this.client = client;
    }

    boolean alreadyInitialized() {
        return client.getDatabase(DATABASE).getCollection(COLLECTION).estimatedDocumentCount() > 0;
    }

    @SneakyThrows
    void initialize() {

        loadSampleData();
        createVectorIndex();
    }

    @SneakyThrows
    void createVectorIndex() {

        log.atLevel(Level.INFO).log("creating vector index");
        client.getDatabase(DATABASE).runCommand(createSearchIndexDefinition());
        Thread.sleep(5000); // this takes time
        log.atLevel(Level.INFO).log("index 'plot_vector_index' created");
    }

    void loadSampleData() throws IOException, InterruptedException {

        log.atLevel(Level.INFO).log("Loading movies {}", MOVIES_JSON);

        ClassPathResource resource = new ClassPathResource(MOVIES_JSON);
        InputStream stream = resource.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readerFor(JsonNode.class).readTree(stream);

        if (node.isArray()) {

            Iterator<JsonNode> elements = node.elements();
            List<InsertOneModel<Document>> bulk = new ArrayList<>(node.size());

            while (elements.hasNext()) {
                bulk.add(new InsertOneModel<>(Document.parse(elements.next().toString())));
            }

            client.getDatabase(DATABASE).getCollection(COLLECTION).bulkWrite(bulk);
            log.atLevel(Level.INFO).log("Created {} movies in {}.{}", node.size(), DATABASE, COLLECTION);
        }

        Thread.sleep(2000); // give writes a little time to complete'
    }

    private org.bson.Document createSearchIndexDefinition() {

        List<Document> vectorFields = List.of(new org.bson.Document().append("type", "vector")
            .append("path", "plot_embedding")
            .append("numDimensions", 1536)
            .append("similarity", "cosine"));

        return new org.bson.Document().append("createSearchIndexes", COLLECTION)
            .append("indexes",
                List.of(new org.bson.Document().append("name", "plot_vector_index")
                    .append("type", "vectorSearch")
                    .append("definition", new org.bson.Document("fields", vectorFields))));
    }
}
