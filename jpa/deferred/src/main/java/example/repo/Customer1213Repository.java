package example.repo;

import example.model.Customer1213;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1213Repository extends CrudRepository<Customer1213, Long> {

	List<Customer1213> findByLastName(String lastName);
}
