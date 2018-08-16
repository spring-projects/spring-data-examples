package example.repo;

import example.model.Customer1056;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1056Repository extends CrudRepository<Customer1056, Long> {

	List<Customer1056> findByLastName(String lastName);
}
