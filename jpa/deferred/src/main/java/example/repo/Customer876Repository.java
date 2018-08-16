package example.repo;

import example.model.Customer876;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer876Repository extends CrudRepository<Customer876, Long> {

	List<Customer876> findByLastName(String lastName);
}
