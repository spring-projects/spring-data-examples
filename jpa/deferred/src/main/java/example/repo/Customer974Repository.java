package example.repo;

import example.model.Customer974;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer974Repository extends CrudRepository<Customer974, Long> {

	List<Customer974> findByLastName(String lastName);
}
