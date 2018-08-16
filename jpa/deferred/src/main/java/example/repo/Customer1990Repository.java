package example.repo;

import example.model.Customer1990;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1990Repository extends CrudRepository<Customer1990, Long> {

	List<Customer1990> findByLastName(String lastName);
}
