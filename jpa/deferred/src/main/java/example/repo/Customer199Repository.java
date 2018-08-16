package example.repo;

import example.model.Customer199;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer199Repository extends CrudRepository<Customer199, Long> {

	List<Customer199> findByLastName(String lastName);
}
