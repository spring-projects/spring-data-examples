package example.repo;

import example.model.Customer1938;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1938Repository extends CrudRepository<Customer1938, Long> {

	List<Customer1938> findByLastName(String lastName);
}
