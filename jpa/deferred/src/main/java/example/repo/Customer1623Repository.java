package example.repo;

import example.model.Customer1623;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1623Repository extends CrudRepository<Customer1623, Long> {

	List<Customer1623> findByLastName(String lastName);
}
