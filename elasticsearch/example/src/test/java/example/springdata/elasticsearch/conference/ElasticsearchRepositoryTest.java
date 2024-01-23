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
package example.springdata.elasticsearch.conference;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test case to show Spring Data Elasticsearch Repository functionality.
 *
 * @author Haibo Liu
 */
class ElasticsearchRepositoryTest extends AbstractContainerBaseTest {

	@Autowired ConferenceRepository repository;

	@Test
	void textSearch() {

		var expectedDate = LocalDate.parse("2014-10-29", FORMAT);
		var expectedWord = "java";

		var result = repository.findAllByKeywordsContainsAndDateAfter(expectedWord, expectedDate);

		assertThat(result).hasSize(3);

		result.forEach(it -> verify(it, expectedWord, expectedDate));
	}

	private void verify(Conference it, String expectedWord, LocalDate expectedDate) {

		assertThat(it.getKeywords()).contains(expectedWord);
		assertThat(it.getDate()).isAfter(expectedDate);
	}
}
