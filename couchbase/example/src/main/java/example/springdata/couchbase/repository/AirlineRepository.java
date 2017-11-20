package example.springdata.couchbase.repository;

import example.springdata.couchbase.model.Airline;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository interface to manage {@link Airline} instances.
 *
 * @author Chandana Kithalagama
 */
public interface AirlineRepository extends CrudRepository<Airline, Integer>{

	@Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND name = $1")
	List<Airline> findAirlineByName(String name);
}
