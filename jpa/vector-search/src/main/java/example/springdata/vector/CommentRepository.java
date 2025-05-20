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
package example.springdata.vector;

import org.springframework.data.domain.Score;
import org.springframework.data.domain.SearchResults;
import org.springframework.data.domain.Vector;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, String> {

	SearchResults<Comment> searchTop10ByCountryAndEmbeddingNear(String country, Vector vector, Score distance);

	@Query("""
			SELECT c, cosine_distance(c.embedding, :embedding) as distance FROM Comment c
			WHERE c.country = :country
				AND cosine_distance(c.embedding, :embedding) <= :distance
			ORDER BY distance asc""")
	SearchResults<Comment> searchAnnotated(String country, Vector embedding, Score distance);
}
