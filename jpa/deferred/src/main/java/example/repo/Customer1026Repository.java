package example.repo;

import example.model.Customer1026;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1026Repository extends CrudRepository<Customer1026, Long> {

	List<Customer1026> findByLastName(String lastName);
}
