package example.repo;

import example.model.Customer1842;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1842Repository extends CrudRepository<Customer1842, Long> {

	List<Customer1842> findByLastName(String lastName);
}
