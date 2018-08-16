package example.repo;

import example.model.Customer1008;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1008Repository extends CrudRepository<Customer1008, Long> {

	List<Customer1008> findByLastName(String lastName);
}
