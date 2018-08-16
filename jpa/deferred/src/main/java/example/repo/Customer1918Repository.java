package example.repo;

import example.model.Customer1918;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1918Repository extends CrudRepository<Customer1918, Long> {

	List<Customer1918> findByLastName(String lastName);
}
