package example.repo;

import example.model.Customer442;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer442Repository extends CrudRepository<Customer442, Long> {

	List<Customer442> findByLastName(String lastName);
}
