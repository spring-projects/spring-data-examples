package example.repo;

import example.model.Customer146;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer146Repository extends CrudRepository<Customer146, Long> {

	List<Customer146> findByLastName(String lastName);
}
