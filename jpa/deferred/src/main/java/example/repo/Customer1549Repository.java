package example.repo;

import example.model.Customer1549;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1549Repository extends CrudRepository<Customer1549, Long> {

	List<Customer1549> findByLastName(String lastName);
}
