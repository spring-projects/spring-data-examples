package example.repo;

import example.model.Customer632;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer632Repository extends CrudRepository<Customer632, Long> {

	List<Customer632> findByLastName(String lastName);
}
