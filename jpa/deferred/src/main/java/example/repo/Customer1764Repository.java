package example.repo;

import example.model.Customer1764;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1764Repository extends CrudRepository<Customer1764, Long> {

	List<Customer1764> findByLastName(String lastName);
}
