package example.repo;

import example.model.Customer1396;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1396Repository extends CrudRepository<Customer1396, Long> {

	List<Customer1396> findByLastName(String lastName);
}
