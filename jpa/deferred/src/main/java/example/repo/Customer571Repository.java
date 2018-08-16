package example.repo;

import example.model.Customer571;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer571Repository extends CrudRepository<Customer571, Long> {

	List<Customer571> findByLastName(String lastName);
}
