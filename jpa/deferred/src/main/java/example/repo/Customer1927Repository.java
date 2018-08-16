package example.repo;

import example.model.Customer1927;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1927Repository extends CrudRepository<Customer1927, Long> {

	List<Customer1927> findByLastName(String lastName);
}
