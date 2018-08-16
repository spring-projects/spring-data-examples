package example.repo;

import example.model.Customer1340;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1340Repository extends CrudRepository<Customer1340, Long> {

	List<Customer1340> findByLastName(String lastName);
}
