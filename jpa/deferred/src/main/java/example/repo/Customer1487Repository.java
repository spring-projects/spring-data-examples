package example.repo;

import example.model.Customer1487;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1487Repository extends CrudRepository<Customer1487, Long> {

	List<Customer1487> findByLastName(String lastName);
}
