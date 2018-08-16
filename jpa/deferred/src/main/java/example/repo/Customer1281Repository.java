package example.repo;

import example.model.Customer1281;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1281Repository extends CrudRepository<Customer1281, Long> {

	List<Customer1281> findByLastName(String lastName);
}
