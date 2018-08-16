package example.repo;

import example.model.Customer1924;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1924Repository extends CrudRepository<Customer1924, Long> {

	List<Customer1924> findByLastName(String lastName);
}
