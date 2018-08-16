package example.repo;

import example.model.Customer1724;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1724Repository extends CrudRepository<Customer1724, Long> {

	List<Customer1724> findByLastName(String lastName);
}
