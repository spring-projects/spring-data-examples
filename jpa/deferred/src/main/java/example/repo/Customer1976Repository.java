package example.repo;

import example.model.Customer1976;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1976Repository extends CrudRepository<Customer1976, Long> {

	List<Customer1976> findByLastName(String lastName);
}
