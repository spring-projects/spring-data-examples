package example.repo;

import example.model.Customer1378;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1378Repository extends CrudRepository<Customer1378, Long> {

	List<Customer1378> findByLastName(String lastName);
}
