/*
 * Copyright 2025 the original author or authors.
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.data.domain.Limit;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.VectorSearchOperation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.RepositoryMethodContext;
import org.springframework.data.repository.core.support.RepositoryMetadataAccess;

class AtlasRepositoryFragment<T> implements AtlasRepository<T>, RepositoryMetadataAccess {

    private final MongoOperations mongoOperations;

    public AtlasRepositoryFragment(@Autowired MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> vectorSearch(String index, String path, List<Double> vector) {

        RepositoryMethodContext methodContext = RepositoryMethodContext.getContext();

        Class<?> domainType = resolveDomainType(methodContext.getMetadata());

				VectorSearchOperation $vectorSearch = VectorSearchOperation.search(index).path(path).vector(vector)
						.limit(Limit.of(10)).numCandidates(150);

				Aggregation aggregation = Aggregation.newAggregation($vectorSearch);

        return (List<T>) mongoOperations.aggregate(aggregation, mongoOperations.getCollectionName(domainType), domainType).getMappedResults();
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> resolveDomainType(RepositoryMetadata metadata) {

        // resolve the actual generic type argument of the AtlasRepository<T>.
        return (Class<T>) ResolvableType.forClass(metadata.getRepositoryInterface())
            .as(AtlasRepository.class)
            .getGeneric(0)
            .resolve();
    }

}
