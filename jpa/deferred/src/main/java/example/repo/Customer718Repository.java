package example.repo;

import example.model.Customer718;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer718Repository extends CrudRepository<Customer718, Long> {

	List<Customer718> findByLastName(String lastName);
}
