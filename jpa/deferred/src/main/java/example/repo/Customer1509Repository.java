package example.repo;

import example.model.Customer1509;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1509Repository extends CrudRepository<Customer1509, Long> {

	List<Customer1509> findByLastName(String lastName);
}
