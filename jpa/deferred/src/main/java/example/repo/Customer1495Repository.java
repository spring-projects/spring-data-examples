package example.repo;

import example.model.Customer1495;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1495Repository extends CrudRepository<Customer1495, Long> {

	List<Customer1495> findByLastName(String lastName);
}
