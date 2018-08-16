package example.repo;

import example.model.Customer1041;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1041Repository extends CrudRepository<Customer1041, Long> {

	List<Customer1041> findByLastName(String lastName);
}
