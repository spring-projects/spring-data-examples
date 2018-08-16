package example.repo;

import example.model.Customer1000;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1000Repository extends CrudRepository<Customer1000, Long> {

	List<Customer1000> findByLastName(String lastName);
}
