package example.repo;

import example.model.Customer1052;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1052Repository extends CrudRepository<Customer1052, Long> {

	List<Customer1052> findByLastName(String lastName);
}
