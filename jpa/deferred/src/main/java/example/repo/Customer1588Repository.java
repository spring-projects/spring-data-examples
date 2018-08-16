package example.repo;

import example.model.Customer1588;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1588Repository extends CrudRepository<Customer1588, Long> {

	List<Customer1588> findByLastName(String lastName);
}
