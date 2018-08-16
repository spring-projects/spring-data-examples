package example.repo;

import example.model.Customer1879;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1879Repository extends CrudRepository<Customer1879, Long> {

	List<Customer1879> findByLastName(String lastName);
}
