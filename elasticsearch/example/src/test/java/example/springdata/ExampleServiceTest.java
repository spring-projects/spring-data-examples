package example.springdata;

import example.springdata.elasticsearch.Application;
import example.springdata.elasticsearch.ExampleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import example.springdata.elasticsearch.model.Conference;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ExampleServiceTest {

    @Autowired
    ElasticsearchTemplate template;

    @Autowired
    ExampleService exampleService;

    @Test
    public void textSearch() {
        exampleService.textSearch();
    }

    @Test
    public void getspatialSearch() {
        exampleService.geoSearch();
    }

    @Test
    public void termFacet() {
        exampleService.termFacetOnKeywords();
    }

    @Test
    public void histogramFacetOnDate() {
        exampleService.histogramFacetOnDate();
    }


}