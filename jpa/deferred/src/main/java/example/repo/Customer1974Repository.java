package example.repo;

import example.model.Customer1974;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1974Repository extends CrudRepository<Customer1974, Long> {

	List<Customer1974> findByLastName(String lastName);
}
