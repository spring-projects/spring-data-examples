package example.repo;

import example.model.Customer496;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer496Repository extends CrudRepository<Customer496, Long> {

	List<Customer496> findByLastName(String lastName);
}
