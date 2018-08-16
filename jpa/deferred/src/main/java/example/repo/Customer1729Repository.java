package example.repo;

import example.model.Customer1729;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1729Repository extends CrudRepository<Customer1729, Long> {

	List<Customer1729> findByLastName(String lastName);
}
