package example.repo;

import example.model.Customer777;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer777Repository extends CrudRepository<Customer777, Long> {

	List<Customer777> findByLastName(String lastName);
}
