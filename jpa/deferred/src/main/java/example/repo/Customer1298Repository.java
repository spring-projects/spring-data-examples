package example.repo;

import example.model.Customer1298;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1298Repository extends CrudRepository<Customer1298, Long> {

	List<Customer1298> findByLastName(String lastName);
}
