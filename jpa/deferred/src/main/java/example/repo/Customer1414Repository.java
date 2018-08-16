package example.repo;

import example.model.Customer1414;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1414Repository extends CrudRepository<Customer1414, Long> {

	List<Customer1414> findByLastName(String lastName);
}
