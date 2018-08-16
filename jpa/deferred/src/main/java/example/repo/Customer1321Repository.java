package example.repo;

import example.model.Customer1321;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1321Repository extends CrudRepository<Customer1321, Long> {

	List<Customer1321> findByLastName(String lastName);
}
