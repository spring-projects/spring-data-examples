package example.springdata.elasticsearch;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;

import example.springdata.elasticsearch.model.Conference;
import example.springdata.elasticsearch.repository.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;


/**
 * @author Artur Konczak
 */
@Configuration
@EnableAutoConfiguration
public class TestsConfiguration {

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
		repository.save(Conference.builder().date("2014-11-06").name("Spring eXchange 2014 - London").keywords(Arrays.asList("java", "spring")).location("51.500152, -0.126236").build());
		repository.save(Conference.builder().date("2014-12-07").name("Scala eXchange 2014 - London").keywords(Arrays.asList("scala", "play", "java")).location("51.500152, -0.126236").build());
		repository.save(Conference.builder().date("2014-11-20").name("Elasticsearch 2014 - Berlin").keywords(Arrays.asList("java", "elasticsearch", "kibana")).location("52.5234051, 13.4113999").build());
		repository.save(Conference.builder().date("2014-11-12").name("AWS London 2014").keywords(Arrays.asList("cloud", "aws")).location("51.500152, -0.126236").build());
		repository.save(Conference.builder().date("2014-10-04").name("JDD14 - Cracow").keywords(Arrays.asList("java", "spring")).location("50.0646501, 19.9449799").build());
	}
}
