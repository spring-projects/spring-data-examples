/*
 * Copyright 2019-2021 the original author or authors.
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
package example.springdata.elasticsearch.conference;

import static org.assertj.core.api.Assertions.*;

import example.springdata.elasticsearch.util.EnabledOnElasticsearch;
import reactor.test.StepVerifier;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test case to show reactive Spring Data Elasticsearch repository functionality.
 *
 * @author Christoph Strobl
 * @author Prakhar Gupta
 */
@EnabledOnElasticsearch
@SpringBootTest(classes = ApplicationConfiguration.class)
class ReactiveElasticsearchRepositoryTest {

	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	ConferenceRepository repository;

	@Test
	void textSearch() {

		var expectedDate = "2014-10-29";
		var expectedWord = "java";

		repository.findAllByKeywordsContainsAndDateAfter(expectedWord, expectedDate) //
				.as(StepVerifier::create) //
				.consumeNextWith(it -> verify(it, expectedWord, expectedDate)) //
				.consumeNextWith(it -> verify(it, expectedWord, expectedDate)) //
				.consumeNextWith(it -> verify(it, expectedWord, expectedDate)) //
				.verifyComplete();
	}

	private void verify(Conference it, String expectedWord, String expectedDate) {

		assertThat(it.getKeywords()).contains(expectedWord);
		try {
			assertThat(format.parse(it.getDate())).isAfter(format.parse(expectedDate));
		} catch (ParseException e) {
			fail("o_O", e);
		}
	}
}
