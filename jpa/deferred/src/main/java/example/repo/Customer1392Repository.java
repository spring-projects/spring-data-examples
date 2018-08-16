package example.repo;

import example.model.Customer1392;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1392Repository extends CrudRepository<Customer1392, Long> {

	List<Customer1392> findByLastName(String lastName);
}
