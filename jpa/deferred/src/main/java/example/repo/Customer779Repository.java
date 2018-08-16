package example.repo;

import example.model.Customer779;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer779Repository extends CrudRepository<Customer779, Long> {

	List<Customer779> findByLastName(String lastName);
}
