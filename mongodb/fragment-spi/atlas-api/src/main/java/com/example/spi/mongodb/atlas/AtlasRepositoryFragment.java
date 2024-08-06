/*
 * Copyright 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.spi.mongodb.atlas;

import java.util.List;
import java.util.Objects;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.repository.core.support.RepositoryMethodContext;
import org.springframework.lang.Nullable;

class AtlasRepositoryFragment<T> implements AtlasRepository<T> {

    private MongoOperations mongoOperations;

    public AtlasRepositoryFragment(@Autowired MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> vectorSearch(String index, String path, List<Double> vector, Limit limit) {

        RepositoryMethodContext metadata = RepositoryMethodContext.currentMethod();
        Objects.requireNonNull(metadata);

        Class<?> domainType = metadata.getRepository().getDomainType();
        System.out.println("domainType: " + domainType);

        Document $vectorSearch = createDocument(index, path, vector, limit, null, null, null);
        Aggregation aggregation = Aggregation.newAggregation(ctx -> $vectorSearch);

        return (List<T>) mongoOperations.aggregate(aggregation, mongoOperations.getCollectionName(domainType), domainType);
    }

    private static Document createDocument(String indexName, String path, List<Double> vector, Limit limit, @Nullable Boolean exact, @Nullable CriteriaDefinition filter, @Nullable Integer numCandidates) {

        Document $vectorSearch = new Document();

        $vectorSearch.append("index", indexName);
        $vectorSearch.append("path", path);
        $vectorSearch.append("queryVector", vector);
        $vectorSearch.append("limit", limit.max());

        if (exact != null) {
            $vectorSearch.append("exact", exact);
        }

        if (filter != null) {
            $vectorSearch.append("filter", filter.getCriteriaObject());
        }

        if (numCandidates != null) {
            $vectorSearch.append("numCandidates", numCandidates);
        }

        return new Document("$vectorSearch", $vectorSearch);
    }
}
