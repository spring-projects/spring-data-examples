package example.repo;

import example.model.Customer406;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer406Repository extends CrudRepository<Customer406, Long> {

	List<Customer406> findByLastName(String lastName);
}
