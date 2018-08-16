package example.repo;

import example.model.Customer1351;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1351Repository extends CrudRepository<Customer1351, Long> {

	List<Customer1351> findByLastName(String lastName);
}
