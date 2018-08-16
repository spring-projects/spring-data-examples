package example.repo;

import example.model.Customer468;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer468Repository extends CrudRepository<Customer468, Long> {

	List<Customer468> findByLastName(String lastName);
}
