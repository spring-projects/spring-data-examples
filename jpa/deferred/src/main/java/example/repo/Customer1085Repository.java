package example.repo;

import example.model.Customer1085;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1085Repository extends CrudRepository<Customer1085, Long> {

	List<Customer1085> findByLastName(String lastName);
}
