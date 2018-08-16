package example.repo;

import example.model.Customer118;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer118Repository extends CrudRepository<Customer118, Long> {

	List<Customer118> findByLastName(String lastName);
}
