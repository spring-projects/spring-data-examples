package example.repo;

import example.model.Customer1268;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1268Repository extends CrudRepository<Customer1268, Long> {

	List<Customer1268> findByLastName(String lastName);
}
