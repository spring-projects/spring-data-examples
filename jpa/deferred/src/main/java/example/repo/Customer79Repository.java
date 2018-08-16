package example.repo;

import example.model.Customer79;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer79Repository extends CrudRepository<Customer79, Long> {

	List<Customer79> findByLastName(String lastName);
}
