package example.repo;

import example.model.Customer1304;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1304Repository extends CrudRepository<Customer1304, Long> {

	List<Customer1304> findByLastName(String lastName);
}
