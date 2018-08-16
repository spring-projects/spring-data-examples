package example.repo;

import example.model.Customer1541;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1541Repository extends CrudRepository<Customer1541, Long> {

	List<Customer1541> findByLastName(String lastName);
}
