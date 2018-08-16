package example.repo;

import example.model.Customer547;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer547Repository extends CrudRepository<Customer547, Long> {

	List<Customer547> findByLastName(String lastName);
}
