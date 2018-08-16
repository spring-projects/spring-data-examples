package example.repo;

import example.model.Customer221;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer221Repository extends CrudRepository<Customer221, Long> {

	List<Customer221> findByLastName(String lastName);
}
