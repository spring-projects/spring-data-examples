package example.repo;

import example.model.Customer1243;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1243Repository extends CrudRepository<Customer1243, Long> {

	List<Customer1243> findByLastName(String lastName);
}
