package example.springdata.elasticsearch;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	ElasticsearchTemplate template;

	@Autowired
	ConferenceRepository repository;

	@Test
	public void textSearch() throws ParseException {
		//given
		String expectedDate = "2014-10-29";
		String expectedWord = "java";
		CriteriaQuery query = new CriteriaQuery(
				new Criteria("_all").contains(expectedWord).and(new Criteria("date").greaterThanEqual(expectedDate)));

		//when
		List<Conference> result = template.queryForList(query, Conference.class);

		//then
		assertThat(result.size(), is(3));
		for (Conference c : result) {
			assertThat(c.getKeywords(), hasItems(expectedWord));
			assertThat(format.parse(c.getDate()), greaterThan(format.parse(expectedDate)));
		}
	}

	@Test
	public void getspatialSearch() {
		//given
		String startLocation = "50.0646501,19.9449799";
		String range = "330mi";//or 530km
		CriteriaQuery query = new CriteriaQuery(
				new Criteria("location").within(startLocation, range));

		//when
		List<Conference> result = template.queryForList(query, Conference.class);

		//then
		assertThat(result.size(), is(2));
	}

	@Test
	public void termFacet() {
		//given
		String termField = "keywords";
		String facetName = "all-keywords";
		FacetedPage<Conference> firstPage = template.queryForPage(new NativeSearchQueryBuilder().withQuery(matchAllQuery())
				.withFacet(new TermFacetRequestBuilder(facetName).allTerms().descCount().fields(termField).build()).build(), Conference.class);

		//when
		TermResult facet = (TermResult) firstPage.getFacet(facetName);

		//then
		assertThat(facet.getTerms().size(), is(8));
		for (Term t : facet.getTerms()) {
			assertThat(t.getTerm(), isOneOf("java", "spring", "scala", "play", "elasticsearch", "kibana", "cloud", "aws"));
		}
	}

	@Test
	public void histogramFacetOnDate() {
		//given
		String termField = "date";
		int interval = 30;
		String facetName = "by-date";
		FacetedPage<Conference> firstPage = template.queryForPage(new NativeSearchQueryBuilder().withQuery(matchAllQuery())
				.withFacet(new HistogramFacetRequestBuilder(facetName).timeUnit(TimeUnit.DAYS).interval(interval).field(termField).build()).build(), Conference.class);

		//when
		HistogramResult facet = (HistogramResult) firstPage.getFacet(facetName);

		//then
		assertThat(facet.getIntervalUnit().size(), is(3));
	}
}
