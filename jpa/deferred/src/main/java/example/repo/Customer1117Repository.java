package example.repo;

import example.model.Customer1117;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1117Repository extends CrudRepository<Customer1117, Long> {

	List<Customer1117> findByLastName(String lastName);
}
