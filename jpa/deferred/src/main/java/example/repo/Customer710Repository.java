package example.repo;

import example.model.Customer710;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer710Repository extends CrudRepository<Customer710, Long> {

	List<Customer710> findByLastName(String lastName);
}
