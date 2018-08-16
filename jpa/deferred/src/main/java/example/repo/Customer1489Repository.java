package example.repo;

import example.model.Customer1489;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1489Repository extends CrudRepository<Customer1489, Long> {

	List<Customer1489> findByLastName(String lastName);
}
