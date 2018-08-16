package example.repo;

import example.model.Customer1413;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1413Repository extends CrudRepository<Customer1413, Long> {

	List<Customer1413> findByLastName(String lastName);
}
