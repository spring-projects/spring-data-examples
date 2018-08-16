package example.repo;

import example.model.Customer1467;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1467Repository extends CrudRepository<Customer1467, Long> {

	List<Customer1467> findByLastName(String lastName);
}
