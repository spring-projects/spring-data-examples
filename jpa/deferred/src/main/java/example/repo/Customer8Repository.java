package example.repo;

import example.model.Customer8;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer8Repository extends CrudRepository<Customer8, Long> {

	List<Customer8> findByLastName(String lastName);
}
