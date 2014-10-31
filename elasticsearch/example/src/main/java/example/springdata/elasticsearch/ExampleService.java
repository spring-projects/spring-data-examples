package example.springdata.elasticsearch;

import example.springdata.elasticsearch.model.Conference;
import example.springdata.elasticsearch.model.ConferenceBuilder;
import example.springdata.elasticsearch.repository.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

@Service
public class ExampleService {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private ConferenceRepository repository;

    @PreDestroy
    public void deleteIndex() {
        template.deleteIndex(Conference.class);
    }

    @PostConstruct
    public void insertDataSample() {
        //remove all documents
        repository.deleteAll();
        template.refresh(Conference.class, true);

        //save data sample
        repository.save(new ConferenceBuilder().date("2014-11-06").name("Spring eXchange 2014 - London").keyword("java", "spring").location("51.500152, -0.126236").build());
        repository.save(new ConferenceBuilder().date("2014-12-07").name("Scala eXchange 2014 - London").keyword("scala", "play", "java").location("51.500152, -0.126236").build());
        repository.save(new ConferenceBuilder().date("2014-11-20").name("Elasticsearch 2014 - Berlin").keyword("java", "elasticsearch", "kibana").location("52.5234051, 13.4113999").build());
        repository.save(new ConferenceBuilder().date("2014-11-12").name("AWS London 2014").keyword("cloud", "aws").location("51.500152, -0.126236").build());
        repository.save(new ConferenceBuilder().date("2014-10-04").name("JDD14 - Cracow").keyword("java", "spring").location("50.0646501, 19.9449799").build());
        //display data
        System.out.println("\nAll available data");
        for (Conference c : repository.findAll()) {
            System.out.println(c);
        }
    }

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

    public void geoSearch() {
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

    public void termFacetOnKeywords() {
        String termField = "keywords";
        String facetName = "all-keywords";
        System.out.printf("%nTerm facets for '%s' field%n", termField);
        FacetedPage<Conference> firstPage = template.queryForPage(new NativeSearchQueryBuilder().withQuery(matchAllQuery())
                .withFacet(new TermFacetRequestBuilder(facetName).allTerms().descCount().fields(termField).build()).build(), Conference.class);

        for (Term term : ((TermResult) firstPage.getFacet(facetName)).getTerms()) {
            System.out.printf("Value '%s' \t\tcount = %d%n", term.getTerm(), term.getCount());
        }
    }

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
