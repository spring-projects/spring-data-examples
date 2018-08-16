package example.repo;

import example.model.Customer1299;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1299Repository extends CrudRepository<Customer1299, Long> {

	List<Customer1299> findByLastName(String lastName);
}
