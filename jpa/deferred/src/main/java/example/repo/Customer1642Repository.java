package example.repo;

import example.model.Customer1642;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1642Repository extends CrudRepository<Customer1642, Long> {

	List<Customer1642> findByLastName(String lastName);
}
