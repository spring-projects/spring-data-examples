package example.repo;

import example.model.Customer1069;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1069Repository extends CrudRepository<Customer1069, Long> {

	List<Customer1069> findByLastName(String lastName);
}
