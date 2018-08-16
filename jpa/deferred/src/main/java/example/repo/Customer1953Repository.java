package example.repo;

import example.model.Customer1953;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1953Repository extends CrudRepository<Customer1953, Long> {

	List<Customer1953> findByLastName(String lastName);
}
