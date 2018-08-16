package example.repo;

import example.model.Customer1941;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1941Repository extends CrudRepository<Customer1941, Long> {

	List<Customer1941> findByLastName(String lastName);
}
