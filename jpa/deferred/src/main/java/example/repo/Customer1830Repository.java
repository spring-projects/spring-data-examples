package example.repo;

import example.model.Customer1830;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1830Repository extends CrudRepository<Customer1830, Long> {

	List<Customer1830> findByLastName(String lastName);
}
