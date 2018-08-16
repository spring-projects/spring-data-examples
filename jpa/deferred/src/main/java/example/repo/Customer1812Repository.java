package example.repo;

import example.model.Customer1812;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1812Repository extends CrudRepository<Customer1812, Long> {

	List<Customer1812> findByLastName(String lastName);
}
