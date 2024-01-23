/*
 * Copyright 2020-2024 the original author or authors.
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
package example.springdata.elasticsearch.conference;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import reactor.test.StepVerifier;

/**
 * Test case to show Spring Data Elasticsearch functionality.
 *
 * @author Christoph Strobl
 * @author Prakhar Gupta
 * @author Peter-Josef Meisch
 * @author Haibo Liu
 */
class ReactiveElasticsearchOperationsTest extends AbstractContainerBaseTest {

	@Autowired ReactiveElasticsearchOperations operations;

	@Test
	void textSearch() {

		var expectedDate = LocalDate.parse( "2014-10-29", FORMAT);
		var expectedWord = "java";
		var query = new CriteriaQuery(
				new Criteria("keywords").contains(expectedWord).and(new Criteria("date").greaterThanEqual(expectedDate)));

		operations.search(query, Conference.class) //
				.as(StepVerifier::create) //
				.consumeNextWith(it -> verify(it, expectedWord, expectedDate)) //
				.consumeNextWith(it -> verify(it, expectedWord, expectedDate)) //
				.consumeNextWith(it -> verify(it, expectedWord, expectedDate)) //
				.verifyComplete();
	}

	private void verify(SearchHit<Conference> hit, String expectedWord, LocalDate expectedDate) {

		assertThat(hit.getContent().getKeywords()).contains(expectedWord);
		assertThat(hit.getContent().getDate()).isAfter(expectedDate);
	}
}
