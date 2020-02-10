/*
 * Copyright 2014-2018 the original author or authors.
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


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

/**
 * Test case to show Spring Data Elasticsearch functionality.
 *
 * @author Artur Konczak
 * @author Oliver Gierke
 * @author Christoph Strobl
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationConfiguration.class)
public class ElasticsearchOperationsTest {

	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired ElasticsearchOperations operations;

	@Test
	public void textSearch() throws ParseException {

		String expectedDate = "2014-10-29";
		String expectedWord = "java";
		CriteriaQuery query = new CriteriaQuery(
				new Criteria("keywords").contains(expectedWord).and(new Criteria("date").greaterThanEqual(expectedDate)));

		SearchHits<Conference> result = operations.search(query, Conference.class, IndexCoordinates.of("conference-index"));

		assertThat(result).hasSize(3);

		for (SearchHit<Conference> conference : result) {
			assertThat(conference.getContent().getKeywords()).contains(expectedWord);
			assertThat(format.parse(conference.getContent().getDate())).isAfter(format.parse(expectedDate));
		}
	}

	@Test
	public void geoSpatialSearch() {

		GeoPoint startLocation = new GeoPoint(50.0646501D, 19.9449799D);
		String range = "330mi"; // or 530km
		CriteriaQuery query = new CriteriaQuery(new Criteria("location").within(startLocation, range));

		SearchHits<Conference> result = operations.search(query, Conference.class, IndexCoordinates.of("conference-index"));

		assertThat(result).hasSize(2);
	}
}
