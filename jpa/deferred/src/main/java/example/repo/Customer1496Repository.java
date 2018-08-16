package example.repo;

import example.model.Customer1496;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1496Repository extends CrudRepository<Customer1496, Long> {

	List<Customer1496> findByLastName(String lastName);
}
