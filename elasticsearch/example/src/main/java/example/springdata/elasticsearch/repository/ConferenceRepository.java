package example.springdata.elasticsearch.repository;

import example.springdata.elasticsearch.model.Conference;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Artur Konczak
 */
public interface ConferenceRepository extends ElasticsearchRepository<Conference, String> {

}
