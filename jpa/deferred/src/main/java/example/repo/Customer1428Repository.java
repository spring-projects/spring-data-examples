package example.repo;

import example.model.Customer1428;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1428Repository extends CrudRepository<Customer1428, Long> {

	List<Customer1428> findByLastName(String lastName);
}
