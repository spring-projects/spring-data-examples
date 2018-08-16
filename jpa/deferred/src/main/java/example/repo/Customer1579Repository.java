package example.repo;

import example.model.Customer1579;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1579Repository extends CrudRepository<Customer1579, Long> {

	List<Customer1579> findByLastName(String lastName);
}
