package example.repo;

import example.model.Customer1313;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1313Repository extends CrudRepository<Customer1313, Long> {

	List<Customer1313> findByLastName(String lastName);
}
