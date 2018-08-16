package example.repo;

import example.model.Customer938;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer938Repository extends CrudRepository<Customer938, Long> {

	List<Customer938> findByLastName(String lastName);
}
