package example.repo;

import example.model.Customer1632;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1632Repository extends CrudRepository<Customer1632, Long> {

	List<Customer1632> findByLastName(String lastName);
}
