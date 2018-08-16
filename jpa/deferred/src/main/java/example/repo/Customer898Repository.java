package example.repo;

import example.model.Customer898;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer898Repository extends CrudRepository<Customer898, Long> {

	List<Customer898> findByLastName(String lastName);
}
