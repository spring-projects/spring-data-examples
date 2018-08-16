package example.repo;

import example.model.Customer385;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer385Repository extends CrudRepository<Customer385, Long> {

	List<Customer385> findByLastName(String lastName);
}
