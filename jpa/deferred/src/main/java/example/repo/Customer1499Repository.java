package example.repo;

import example.model.Customer1499;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1499Repository extends CrudRepository<Customer1499, Long> {

	List<Customer1499> findByLastName(String lastName);
}
