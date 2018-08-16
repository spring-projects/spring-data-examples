package example.repo;

import example.model.Customer343;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer343Repository extends CrudRepository<Customer343, Long> {

	List<Customer343> findByLastName(String lastName);
}
