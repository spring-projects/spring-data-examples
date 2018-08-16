package example.repo;

import example.model.Customer1876;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1876Repository extends CrudRepository<Customer1876, Long> {

	List<Customer1876> findByLastName(String lastName);
}
