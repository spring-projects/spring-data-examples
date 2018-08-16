package example.repo;

import example.model.Customer1964;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1964Repository extends CrudRepository<Customer1964, Long> {

	List<Customer1964> findByLastName(String lastName);
}
