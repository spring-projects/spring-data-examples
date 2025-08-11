/*
 * Copyright 2020-2021 the original author or authors.
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

import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchTemplate;
import reactor.test.StepVerifier;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.util.Assert;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * Test case to show Spring Data Elasticsearch functionality.
 *
 * @author Christoph Strobl
 * @author Prakhar Gupta
 * @author omniCoder77
 * @author Peter-Josef Meisch
 */
@SpringBootTest(
		classes = { ApplicationConfiguration.class, ReactiveElasticsearchOperationsTest.TestConfiguration.class })
@Testcontainers
class ReactiveElasticsearchOperationsTest {

	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Container //
	private static final ElasticsearchContainer container = new ElasticsearchContainer(
			DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:9.0.3")) //
			.withPassword("foobar") //
			.withReuse(true);

	@Configuration
	static class TestConfiguration extends ReactiveElasticsearchConfiguration {
		@Override
		public ClientConfiguration clientConfiguration() {

			Assert.notNull(container, "TestContainer is not initialized!");

			return ClientConfiguration.builder() //
					.connectedTo(container.getHttpHostAddress()) //
					.usingSsl(container.createSslContextFromCa()) //
					.withBasicAuth("elastic", "foobar") //
					.build();
		}
	}

	@Autowired ReactiveElasticsearchOperations operations;
	@Autowired
    ReactiveElasticsearchTemplate template;

	@Test
	void textSearch() {

		var expectedDate = "2014-10-29";
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

	private void verify(SearchHit<Conference> hit, String expectedWord, String expectedDate) {

		assertThat(hit.getContent().getKeywords()).contains(expectedWord);
		try {
			assertThat(format.parse(hit.getContent().getDate())).isAfter(format.parse(expectedDate));
		} catch (ParseException e) {
			fail("o_O", e);
		}
	}

    @Test
    void shouldUpdateConferenceKeywordsReactive() {
        var conferenceName = "JDD14 - Cracow";
        var newKeyword = "java-ee";

        var initialQuery = new CriteriaQuery(new Criteria("name").is(conferenceName));

        StepVerifier.create(
                        template
                                .search(initialQuery, Conference.class)
                                .next()
                                .flatMap(searchHit -> {
                                    assert searchHit != null;
                                    Conference conference = searchHit.getContent();
                                    String conferenceId = searchHit.getId();

                                    int originalKeywordsCount = conference.getKeywords().size();
                                    assert !conference.getKeywords().contains(newKeyword);

                                    conference.getKeywords().add(newKeyword);

                                    return template.save(conference)
                                            .then(template.get(conferenceId, Conference.class))
                                            .map(updatedConference -> {
                                                assert updatedConference != null;
                                                assert updatedConference.getKeywords().contains(newKeyword);
                                                assert updatedConference.getKeywords().size() == originalKeywordsCount + 1;
                                                return updatedConference;
                                            });
                                })
                ).expectNextCount(1)
                .verifyComplete();
    }

}