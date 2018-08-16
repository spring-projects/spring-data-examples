package example.repo;

import example.model.Customer661;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer661Repository extends CrudRepository<Customer661, Long> {

	List<Customer661> findByLastName(String lastName);
}
