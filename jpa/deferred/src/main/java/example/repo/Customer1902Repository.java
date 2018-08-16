package example.repo;

import example.model.Customer1902;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1902Repository extends CrudRepository<Customer1902, Long> {

	List<Customer1902> findByLastName(String lastName);
}
