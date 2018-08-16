package example.repo;

import example.model.Customer1898;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1898Repository extends CrudRepository<Customer1898, Long> {

	List<Customer1898> findByLastName(String lastName);
}
