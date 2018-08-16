package example.repo;

import example.model.Customer1194;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1194Repository extends CrudRepository<Customer1194, Long> {

	List<Customer1194> findByLastName(String lastName);
}
