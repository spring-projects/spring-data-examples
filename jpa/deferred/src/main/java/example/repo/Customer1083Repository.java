package example.repo;

import example.model.Customer1083;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1083Repository extends CrudRepository<Customer1083, Long> {

	List<Customer1083> findByLastName(String lastName);
}
