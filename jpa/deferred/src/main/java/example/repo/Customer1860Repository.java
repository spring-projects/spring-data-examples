package example.repo;

import example.model.Customer1860;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1860Repository extends CrudRepository<Customer1860, Long> {

	List<Customer1860> findByLastName(String lastName);
}
