package example.repo;

import example.model.Customer918;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer918Repository extends CrudRepository<Customer918, Long> {

	List<Customer918> findByLastName(String lastName);
}
