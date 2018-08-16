package example.repo;

import example.model.Customer1365;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1365Repository extends CrudRepository<Customer1365, Long> {

	List<Customer1365> findByLastName(String lastName);
}
