/**
 * 
 */
package example.springdata.aerospike;

import java.util.List;

import org.springframework.data.aerospike.repository.AerospikeRepository;

import example.springdata.aerospike.data.Person;

/**
 *
 *
 * @author Peter Milne
 * @author Jean Mercier
 *
 */
public interface PersonRepository extends AerospikeRepository<Person, String> {

	List<Person> findByName(String name);

	List<Person> findByNameStartsWith(String prefix);

}
