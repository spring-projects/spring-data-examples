package example.springdata.elasticsearch;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import example.springdata.elasticsearch.model.Conference;
import example.springdata.elasticsearch.repository.ConferenceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.facet.request.HistogramFacetRequestBuilder;
import org.springframework.data.elasticsearch.core.facet.request.TermFacetRequestBuilder;
import org.springframework.data.elasticsearch.core.facet.result.HistogramResult;
import org.springframework.data.elasticsearch.core.facet.result.IntervalUnit;
import org.springframework.data.elasticsearch.core.facet.result.Term;
import org.springframework.data.elasticsearch.core.facet.result.TermResult;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Artur Konczak
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestsConfiguration.class)
public class BasicOperationsTest {

	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Autowired
	ElasticsearchTemplate template;

	@Autowired
	ConferenceRepository repository;

	@Test
	public void textSearch() {
		String expectedDate = "2014-10-29";
		String expectedWord = "java";
		System.out.printf("%nSimple text search (any document with word '%s' and with date bigger them '%s')%n", expectedWord, expectedDate);
		CriteriaQuery query = new CriteriaQuery(
				new Criteria("_all").contains(expectedWord).and(new Criteria("date").greaterThanEqual(expectedDate)));

		List<Conference> result = template.queryForList(query, Conference.class);

		System.out.printf("Result %d of %d%n", result.size(), repository.count());
		for (Conference c : result) {
			System.out.println(c);
		}
	}

	@Test
	public void getspatialSearch() {
		String startLocation = "50.0646501,19.9449799";
		String range = "330mi";
		System.out.printf("%nSimple search using geospatial values, startPoint '%s' and range '%s'%n", startLocation, range);
		CriteriaQuery query = new CriteriaQuery(
				new Criteria("location").within(startLocation, range)); //or 530km

		List<Conference> result = template.queryForList(query, Conference.class);

		System.out.printf("Result %d of %d%n", result.size(), repository.count());
		for (Conference c : result) {
			System.out.println(c);
		}
	}

	@Test
	public void termFacet() {
		String termField = "keywords";
		String facetName = "all-keywords";
		System.out.printf("%nTerm facets for '%s' field%n", termField);
		FacetedPage<Conference> firstPage = template.queryForPage(new NativeSearchQueryBuilder().withQuery(matchAllQuery())
				.withFacet(new TermFacetRequestBuilder(facetName).allTerms().descCount().fields(termField).build()).build(), Conference.class);

		for (Term term : ((TermResult) firstPage.getFacet(facetName)).getTerms()) {
			System.out.printf("Value '%s' \t\tcount = %d%n", term.getTerm(), term.getCount());
		}
	}

	@Test
	public void histogramFacetOnDate() {
		String termField = "date";
		int interval = 30;
		String facetName = "by-date";
		System.out.printf("%nHistogram facets for '%s' field (%s days interval)%n", termField, interval);
		FacetedPage<Conference> firstPage = template.queryForPage(new NativeSearchQueryBuilder().withQuery(matchAllQuery())
				.withFacet(new HistogramFacetRequestBuilder(facetName).timeUnit(TimeUnit.DAYS).interval(interval).field(termField).build()).build(), Conference.class);

		for (IntervalUnit intervalUnit : ((HistogramResult) firstPage.getFacet(facetName)).getIntervalUnit()) {
			System.out.printf("Bucket '%s' \t\tcount = %d%n", format.format(new Date(intervalUnit.getKey())), intervalUnit.getCount());
		}
	}
}
