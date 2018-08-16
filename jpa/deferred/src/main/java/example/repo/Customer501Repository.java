package example.repo;

import example.model.Customer501;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer501Repository extends CrudRepository<Customer501, Long> {

	List<Customer501> findByLastName(String lastName);
}
