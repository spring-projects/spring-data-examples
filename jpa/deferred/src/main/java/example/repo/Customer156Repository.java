package example.repo;

import example.model.Customer156;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer156Repository extends CrudRepository<Customer156, Long> {

	List<Customer156> findByLastName(String lastName);
}
