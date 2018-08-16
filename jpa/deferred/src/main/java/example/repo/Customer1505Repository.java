package example.repo;

import example.model.Customer1505;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1505Repository extends CrudRepository<Customer1505, Long> {

	List<Customer1505> findByLastName(String lastName);
}
