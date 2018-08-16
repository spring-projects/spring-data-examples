package example.repo;

import example.model.Customer1025;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1025Repository extends CrudRepository<Customer1025, Long> {

	List<Customer1025> findByLastName(String lastName);
}
