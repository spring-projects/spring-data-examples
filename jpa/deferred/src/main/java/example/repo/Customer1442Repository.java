package example.repo;

import example.model.Customer1442;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1442Repository extends CrudRepository<Customer1442, Long> {

	List<Customer1442> findByLastName(String lastName);
}
