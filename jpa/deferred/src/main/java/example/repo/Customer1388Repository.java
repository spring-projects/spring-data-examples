package example.repo;

import example.model.Customer1388;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1388Repository extends CrudRepository<Customer1388, Long> {

	List<Customer1388> findByLastName(String lastName);
}
