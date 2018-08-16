package example.repo;

import example.model.Customer1031;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1031Repository extends CrudRepository<Customer1031, Long> {

	List<Customer1031> findByLastName(String lastName);
}
