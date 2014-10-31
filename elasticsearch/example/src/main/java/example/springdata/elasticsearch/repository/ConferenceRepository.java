package example.springdata.elasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import example.springdata.elasticsearch.model.Conference;

/**
 * Created by akonczak on 25/10/2014.
 */
public interface ConferenceRepository extends ElasticsearchRepository<Conference, String> {
}
