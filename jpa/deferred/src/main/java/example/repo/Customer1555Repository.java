package example.repo;

import example.model.Customer1555;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1555Repository extends CrudRepository<Customer1555, Long> {

	List<Customer1555> findByLastName(String lastName);
}
