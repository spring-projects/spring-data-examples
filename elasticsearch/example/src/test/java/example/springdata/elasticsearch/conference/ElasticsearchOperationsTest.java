/*
 * Copyright 2014-2015 the original author or authors.
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

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import example.springdata.elasticsearch.conference.ApplicationConfiguration;
import example.springdata.elasticsearch.conference.Conference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.facet.request.HistogramFacetRequestBuilder;
import org.springframework.data.elasticsearch.core.facet.request.TermFacetRequestBuilder;
import org.springframework.data.elasticsearch.core.facet.result.HistogramResult;
import org.springframework.data.elasticsearch.core.facet.result.TermResult;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test case to show Spring Data Elasticsearch functionality.
 * 
 * @author Artur Konczak
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationConfiguration.class)
public class ElasticsearchOperationsTest {

	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired ElasticsearchOperations operations;

	@Test
	public void textSearch() throws ParseException {

		String expectedDate = "2014-10-29";
		String expectedWord = "java";
		CriteriaQuery query = new CriteriaQuery(new Criteria("_all").contains(expectedWord).and(
				new Criteria("date").greaterThanEqual(expectedDate)));

		List<Conference> result = operations.queryForList(query, Conference.class);

		assertThat(result, hasSize(3));

		for (Conference conference : result) {
			assertThat(conference.getKeywords(), hasItem(expectedWord));
			assertThat(format.parse(conference.getDate()), greaterThan(format.parse(expectedDate)));
		}
	}

	@Test
	public void geoSpatialSearch() {

		String startLocation = "50.0646501,19.9449799";
		String range = "330mi"; // or 530km
		CriteriaQuery query = new CriteriaQuery(new Criteria("location").within(startLocation, range));

		List<Conference> result = operations.queryForList(query, Conference.class);

		assertThat(result, hasSize(2));
	}

	@Test
	public void termFacet() {

		String termField = "keywords";
		String facetName = "all-keywords";

		FacetedPage<Conference> firstPage = operations.queryForPage(//
				new NativeSearchQueryBuilder().//
						withQuery(matchAllQuery()).//
						withFacet(new TermFacetRequestBuilder(facetName).//
								allTerms().//
								descCount().//
								fields(termField).build()).//
						build(), Conference.class);

		TermResult facet = (TermResult) firstPage.getFacet(facetName);

		assertThat(facet.getTerms(), hasSize(8));

		facet.getTerms().forEach(
				term -> {
					assertThat(term.getTerm(),
							isOneOf("java", "spring", "scala", "play", "elasticsearch", "kibana", "cloud", "aws"));
				});
	}

	@Test
	public void histogramFacetOnDate() {

		String termField = "date";
		int interval = 30;
		String facetName = "by-date";

		FacetedPage<Conference> firstPage = operations.queryForPage(new NativeSearchQueryBuilder() //
				.withQuery(matchAllQuery()) //
				.withFacet(new HistogramFacetRequestBuilder(facetName).//
						timeUnit(TimeUnit.DAYS).//
						interval(interval). //
						field(termField).build()).//
				build(), Conference.class);

		HistogramResult facet = (HistogramResult) firstPage.getFacet(facetName);

		assertThat(facet.getIntervalUnit(), hasSize(3));
	}
}
