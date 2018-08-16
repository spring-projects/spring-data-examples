package example.repo;

import example.model.Customer344;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer344Repository extends CrudRepository<Customer344, Long> {

	List<Customer344> findByLastName(String lastName);
}
