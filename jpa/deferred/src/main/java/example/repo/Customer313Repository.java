package example.repo;

import example.model.Customer313;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer313Repository extends CrudRepository<Customer313, Long> {

	List<Customer313> findByLastName(String lastName);
}
