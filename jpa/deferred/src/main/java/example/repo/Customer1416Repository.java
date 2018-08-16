package example.repo;

import example.model.Customer1416;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1416Repository extends CrudRepository<Customer1416, Long> {

	List<Customer1416> findByLastName(String lastName);
}
