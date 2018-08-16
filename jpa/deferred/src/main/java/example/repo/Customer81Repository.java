package example.repo;

import example.model.Customer81;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer81Repository extends CrudRepository<Customer81, Long> {

	List<Customer81> findByLastName(String lastName);
}
