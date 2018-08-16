package example.repo;

import example.model.Customer927;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer927Repository extends CrudRepository<Customer927, Long> {

	List<Customer927> findByLastName(String lastName);
}
