/*
 * Copyright 2025 the original author or authors.
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
package example.springdata.vector;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Score;
import org.springframework.data.domain.SearchResults;
import org.springframework.data.domain.Vector;
import org.springframework.data.mongodb.core.aggregation.VectorSearchOperation;
import org.springframework.data.mongodb.repository.VectorSearch;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, String> {

    @VectorSearch(indexName = "cosine-index", searchType = VectorSearchOperation.SearchType.ANN)
    SearchResults<Comment> searchTop10ByCountryAndEmbeddingNear(String country, Vector vector, Score distance);

    @VectorSearch(indexName = "cosine-index", filter = "{country: ?0}", numCandidates = "#{#limit.max*10}",
        searchType = VectorSearchOperation.SearchType.ANN)
    SearchResults<Comment> searchAnnotated(String country, Vector vector, Score distance, Limit limit);
}
