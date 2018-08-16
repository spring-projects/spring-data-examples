package example.repo;

import example.model.Customer1531;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1531Repository extends CrudRepository<Customer1531, Long> {

	List<Customer1531> findByLastName(String lastName);
}
