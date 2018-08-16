package example.repo;

import example.model.Customer519;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer519Repository extends CrudRepository<Customer519, Long> {

	List<Customer519> findByLastName(String lastName);
}
