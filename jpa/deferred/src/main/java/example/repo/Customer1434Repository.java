package example.repo;

import example.model.Customer1434;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1434Repository extends CrudRepository<Customer1434, Long> {

	List<Customer1434> findByLastName(String lastName);
}
