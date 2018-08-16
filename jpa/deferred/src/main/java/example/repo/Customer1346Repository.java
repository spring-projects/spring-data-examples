package example.repo;

import example.model.Customer1346;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1346Repository extends CrudRepository<Customer1346, Long> {

	List<Customer1346> findByLastName(String lastName);
}
