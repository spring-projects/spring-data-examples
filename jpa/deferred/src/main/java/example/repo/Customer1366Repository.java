package example.repo;

import example.model.Customer1366;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1366Repository extends CrudRepository<Customer1366, Long> {

	List<Customer1366> findByLastName(String lastName);
}
