package example.repo;

import example.model.Customer764;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer764Repository extends CrudRepository<Customer764, Long> {

	List<Customer764> findByLastName(String lastName);
}
