package example.repo;

import example.model.Customer1533;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1533Repository extends CrudRepository<Customer1533, Long> {

	List<Customer1533> findByLastName(String lastName);
}
