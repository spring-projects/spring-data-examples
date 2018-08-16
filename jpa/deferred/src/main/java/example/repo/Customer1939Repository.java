package example.repo;

import example.model.Customer1939;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1939Repository extends CrudRepository<Customer1939, Long> {

	List<Customer1939> findByLastName(String lastName);
}
