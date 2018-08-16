package example.repo;

import example.model.Customer1819;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1819Repository extends CrudRepository<Customer1819, Long> {

	List<Customer1819> findByLastName(String lastName);
}
