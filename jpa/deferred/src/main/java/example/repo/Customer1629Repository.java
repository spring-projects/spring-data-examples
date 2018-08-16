package example.repo;

import example.model.Customer1629;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1629Repository extends CrudRepository<Customer1629, Long> {

	List<Customer1629> findByLastName(String lastName);
}
