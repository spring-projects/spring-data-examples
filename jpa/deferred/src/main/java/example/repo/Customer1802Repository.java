package example.repo;

import example.model.Customer1802;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1802Repository extends CrudRepository<Customer1802, Long> {

	List<Customer1802> findByLastName(String lastName);
}
