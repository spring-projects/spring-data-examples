package example.repo;

import example.model.Customer1519;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1519Repository extends CrudRepository<Customer1519, Long> {

	List<Customer1519> findByLastName(String lastName);
}
