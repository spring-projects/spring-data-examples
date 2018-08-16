package example.repo;

import example.model.Customer1234;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1234Repository extends CrudRepository<Customer1234, Long> {

	List<Customer1234> findByLastName(String lastName);
}
