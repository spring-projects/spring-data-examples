package example.repo;

import example.model.Customer1940;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1940Repository extends CrudRepository<Customer1940, Long> {

	List<Customer1940> findByLastName(String lastName);
}
