package example.repo;

import example.model.Customer556;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer556Repository extends CrudRepository<Customer556, Long> {

	List<Customer556> findByLastName(String lastName);
}
