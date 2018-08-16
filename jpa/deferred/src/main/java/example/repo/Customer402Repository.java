package example.repo;

import example.model.Customer402;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer402Repository extends CrudRepository<Customer402, Long> {

	List<Customer402> findByLastName(String lastName);
}
