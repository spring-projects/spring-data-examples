package example.repo;

import example.model.Customer1356;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1356Repository extends CrudRepository<Customer1356, Long> {

	List<Customer1356> findByLastName(String lastName);
}
