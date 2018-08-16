package example.repo;

import example.model.Customer1930;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1930Repository extends CrudRepository<Customer1930, Long> {

	List<Customer1930> findByLastName(String lastName);
}
