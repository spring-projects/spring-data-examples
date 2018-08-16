package example.repo;

import example.model.Customer1016;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1016Repository extends CrudRepository<Customer1016, Long> {

	List<Customer1016> findByLastName(String lastName);
}
