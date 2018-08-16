package example.repo;

import example.model.Customer1432;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1432Repository extends CrudRepository<Customer1432, Long> {

	List<Customer1432> findByLastName(String lastName);
}
