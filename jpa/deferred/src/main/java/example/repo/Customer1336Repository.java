package example.repo;

import example.model.Customer1336;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1336Repository extends CrudRepository<Customer1336, Long> {

	List<Customer1336> findByLastName(String lastName);
}
