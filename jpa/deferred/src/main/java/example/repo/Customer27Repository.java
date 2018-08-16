package example.repo;

import example.model.Customer27;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer27Repository extends CrudRepository<Customer27, Long> {

	List<Customer27> findByLastName(String lastName);
}
