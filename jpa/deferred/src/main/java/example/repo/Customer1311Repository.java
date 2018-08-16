package example.repo;

import example.model.Customer1311;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1311Repository extends CrudRepository<Customer1311, Long> {

	List<Customer1311> findByLastName(String lastName);
}
