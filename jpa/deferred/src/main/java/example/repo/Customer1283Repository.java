package example.repo;

import example.model.Customer1283;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1283Repository extends CrudRepository<Customer1283, Long> {

	List<Customer1283> findByLastName(String lastName);
}
