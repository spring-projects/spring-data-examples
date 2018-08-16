package example.repo;

import example.model.Customer787;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer787Repository extends CrudRepository<Customer787, Long> {

	List<Customer787> findByLastName(String lastName);
}
