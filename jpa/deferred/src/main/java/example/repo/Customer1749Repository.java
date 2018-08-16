package example.repo;

import example.model.Customer1749;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1749Repository extends CrudRepository<Customer1749, Long> {

	List<Customer1749> findByLastName(String lastName);
}
