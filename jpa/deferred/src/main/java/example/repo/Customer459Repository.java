package example.repo;

import example.model.Customer459;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer459Repository extends CrudRepository<Customer459, Long> {

	List<Customer459> findByLastName(String lastName);
}
