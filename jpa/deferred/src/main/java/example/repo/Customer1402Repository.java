package example.repo;

import example.model.Customer1402;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1402Repository extends CrudRepository<Customer1402, Long> {

	List<Customer1402> findByLastName(String lastName);
}
