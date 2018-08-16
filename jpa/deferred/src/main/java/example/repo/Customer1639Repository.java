package example.repo;

import example.model.Customer1639;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1639Repository extends CrudRepository<Customer1639, Long> {

	List<Customer1639> findByLastName(String lastName);
}
