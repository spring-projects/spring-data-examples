package example.repo;

import example.model.Customer304;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer304Repository extends CrudRepository<Customer304, Long> {

	List<Customer304> findByLastName(String lastName);
}
