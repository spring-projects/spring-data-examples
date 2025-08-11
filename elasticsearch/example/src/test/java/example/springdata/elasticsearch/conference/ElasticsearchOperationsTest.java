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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
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
 * @author Artur Konczak
 * @author Oliver Gierke
 * @author Christoph Strobl
 * @author omniCoder77
 * @author Prakhar Gupta
 * @author Peter-Josef Meisch
 */
@SpringBootTest(classes = { ApplicationConfiguration.class, ElasticsearchOperationsTest.TestConfiguration.class })
@Testcontainers
class ElasticsearchOperationsTest {

	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	@Container //
	private static final ElasticsearchContainer container = new ElasticsearchContainer(
			DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:8.7.0")) //
			.withPassword("foobar") //
			.withReuse(true);

	@Configuration
	static class TestConfiguration extends ElasticsearchConfiguration {
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

	@Autowired ElasticsearchOperations operations;
	@Autowired
    ElasticsearchTemplate template;

	@Test
	void textSearch() throws ParseException {

		var expectedDate = "2014-10-29";
		var expectedWord = "java";
		var query = new CriteriaQuery(
				new Criteria("keywords").contains(expectedWord).and(new Criteria("date").greaterThanEqual(expectedDate)));

		var result = operations.search(query, Conference.class);

		assertThat(result).hasSize(3);

		for (var conference : result) {
			assertThat(conference.getContent().getKeywords()).contains(expectedWord);
			assertThat(format.parse(conference.getContent().getDate())).isAfter(format.parse(expectedDate));
		}
	}

	@Test
	void geoSpatialSearch() {

		var startLocation = new GeoPoint(50.0646501D, 19.9449799D);
		var range = "330mi"; // or 530km
		var query = new CriteriaQuery(new Criteria("location").within(startLocation, range));

		var result = operations.search(query, Conference.class, IndexCoordinates.of("conference-index"));

		assertThat(result).hasSize(2);
	}

    @Test
    void shouldUpdateConferenceKeywords() {
        var conferenceName = "JDD14 - Cracow";
        var newKeyword = "java-ee";

        var initialQuery = new CriteriaQuery(new Criteria("name").is(conferenceName));
        SearchHit<Conference> searchHit = template.searchOne(initialQuery, Conference.class);
        assertThat(searchHit).isNotNull();

        Conference conference = searchHit.getContent();
        String conferenceId = searchHit.getId();

        int originalKeywordsCount = conference.getKeywords().size();
        assertThat(conference.getKeywords()).doesNotContain(newKeyword);

        conference.getKeywords().add(newKeyword);

        template.save(conference);

        Conference updatedConference = template.get(conferenceId, Conference.class);
        assertThat(updatedConference).isNotNull();
        assertThat(updatedConference.getKeywords()).contains(newKeyword);
        assertThat(updatedConference.getKeywords()).hasSize(originalKeywordsCount + 1);
    }
}