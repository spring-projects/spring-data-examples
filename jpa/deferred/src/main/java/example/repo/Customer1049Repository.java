package example.repo;

import example.model.Customer1049;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1049Repository extends CrudRepository<Customer1049, Long> {

	List<Customer1049> findByLastName(String lastName);
}
