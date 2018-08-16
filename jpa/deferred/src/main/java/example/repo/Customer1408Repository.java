package example.repo;

import example.model.Customer1408;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1408Repository extends CrudRepository<Customer1408, Long> {

	List<Customer1408> findByLastName(String lastName);
}
