package example.repo;

import example.model.Customer1421;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1421Repository extends CrudRepository<Customer1421, Long> {

	List<Customer1421> findByLastName(String lastName);
}
