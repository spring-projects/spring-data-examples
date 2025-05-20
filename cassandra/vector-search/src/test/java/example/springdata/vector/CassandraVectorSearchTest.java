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

import static org.springframework.data.domain.ScoringFunction.*;

import example.springdata.cassandra.util.CassandraKeyspace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Score;
import org.springframework.data.domain.ScoringFunction;
import org.springframework.data.domain.SearchResult;
import org.springframework.data.domain.Vector;

@CassandraKeyspace
@SpringBootTest
class CassandraVectorSearchTest {

	@Autowired CommentRepository repository;

	@BeforeEach
	void beforeAll() throws InterruptedException {
		Thread.sleep(5000); // a little time to think
	}

	@Test
	void vectorSearchUsingQueryMethod() {

		Vector vector = Vector.of(0.2001f, 0.32345f, 0.43456f, 0.54567f, 0.65678f);

		repository.searchTop10ByEmbeddingNear(vector, ScoringFunction.cosine())
				.forEach(CassandraVectorSearchTest::printResult);
	}

	@Test
	void vectorSearchUsingRawAtQuery() {

		Vector vector = Vector.of(0.2001f, 0.32345f, 0.43456f, 0.54567f, 0.65678f);

		repository.searchAnnotated(vector, Score.of(0.5, cosine()), Limit.of(10))
				.forEach(CassandraVectorSearchTest::printResult);
	}

	private static void printResult(SearchResult<Comment> result) {
		System.out.printf("score: %s - %s\n", result.getScore(), result.getContent());
	}
}
