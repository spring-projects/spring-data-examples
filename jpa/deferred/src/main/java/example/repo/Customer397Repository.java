package example.repo;

import example.model.Customer397;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer397Repository extends CrudRepository<Customer397, Long> {

	List<Customer397> findByLastName(String lastName);
}
