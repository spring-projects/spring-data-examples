package example.repo;

import example.model.Customer1426;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1426Repository extends CrudRepository<Customer1426, Long> {

	List<Customer1426> findByLastName(String lastName);
}
