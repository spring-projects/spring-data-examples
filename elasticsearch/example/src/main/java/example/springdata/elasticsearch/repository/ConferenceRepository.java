package example.springdata.elasticsearch.repository;

import example.springdata.elasticsearch.model.Conference;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by akonczak on 25/10/2014.
 */
public interface ConferenceRepository extends ElasticsearchRepository<Conference, String> {

}
