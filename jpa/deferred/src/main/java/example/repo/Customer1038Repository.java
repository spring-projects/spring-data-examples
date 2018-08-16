package example.repo;

import example.model.Customer1038;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1038Repository extends CrudRepository<Customer1038, Long> {

	List<Customer1038> findByLastName(String lastName);
}
