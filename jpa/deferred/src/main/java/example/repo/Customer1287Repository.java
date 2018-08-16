package example.repo;

import example.model.Customer1287;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1287Repository extends CrudRepository<Customer1287, Long> {

	List<Customer1287> findByLastName(String lastName);
}
