package example.repo;

import example.model.Customer287;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer287Repository extends CrudRepository<Customer287, Long> {

	List<Customer287> findByLastName(String lastName);
}
