package example.repo;

import example.model.Customer1787;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1787Repository extends CrudRepository<Customer1787, Long> {

	List<Customer1787> findByLastName(String lastName);
}
