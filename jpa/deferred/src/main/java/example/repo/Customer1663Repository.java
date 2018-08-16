package example.repo;

import example.model.Customer1663;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1663Repository extends CrudRepository<Customer1663, Long> {

	List<Customer1663> findByLastName(String lastName);
}
