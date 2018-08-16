package example.repo;

import example.model.Customer1556;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1556Repository extends CrudRepository<Customer1556, Long> {

	List<Customer1556> findByLastName(String lastName);
}
