package example.repo;

import example.model.Customer1522;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1522Repository extends CrudRepository<Customer1522, Long> {

	List<Customer1522> findByLastName(String lastName);
}
