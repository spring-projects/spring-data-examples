package example.repo;

import example.model.Customer1154;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1154Repository extends CrudRepository<Customer1154, Long> {

	List<Customer1154> findByLastName(String lastName);
}
