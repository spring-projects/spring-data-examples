package example.repo;

import example.model.Customer1570;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1570Repository extends CrudRepository<Customer1570, Long> {

	List<Customer1570> findByLastName(String lastName);
}
