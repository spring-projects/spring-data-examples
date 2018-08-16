package example.repo;

import example.model.Customer1359;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1359Repository extends CrudRepository<Customer1359, Long> {

	List<Customer1359> findByLastName(String lastName);
}
