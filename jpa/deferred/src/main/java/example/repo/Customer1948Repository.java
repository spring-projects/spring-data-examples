package example.repo;

import example.model.Customer1948;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1948Repository extends CrudRepository<Customer1948, Long> {

	List<Customer1948> findByLastName(String lastName);
}
