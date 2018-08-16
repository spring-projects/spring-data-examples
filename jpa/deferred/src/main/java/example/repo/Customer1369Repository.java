package example.repo;

import example.model.Customer1369;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1369Repository extends CrudRepository<Customer1369, Long> {

	List<Customer1369> findByLastName(String lastName);
}
