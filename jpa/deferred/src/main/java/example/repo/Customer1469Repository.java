package example.repo;

import example.model.Customer1469;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1469Repository extends CrudRepository<Customer1469, Long> {

	List<Customer1469> findByLastName(String lastName);
}
