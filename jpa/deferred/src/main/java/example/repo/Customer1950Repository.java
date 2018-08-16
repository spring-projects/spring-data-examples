package example.repo;

import example.model.Customer1950;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1950Repository extends CrudRepository<Customer1950, Long> {

	List<Customer1950> findByLastName(String lastName);
}
