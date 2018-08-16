package example.repo;

import example.model.Customer1857;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1857Repository extends CrudRepository<Customer1857, Long> {

	List<Customer1857> findByLastName(String lastName);
}
