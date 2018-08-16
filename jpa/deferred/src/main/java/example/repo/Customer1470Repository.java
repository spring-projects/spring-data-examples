package example.repo;

import example.model.Customer1470;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1470Repository extends CrudRepository<Customer1470, Long> {

	List<Customer1470> findByLastName(String lastName);
}
