package example.repo;

import example.model.Customer1459;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1459Repository extends CrudRepository<Customer1459, Long> {

	List<Customer1459> findByLastName(String lastName);
}
