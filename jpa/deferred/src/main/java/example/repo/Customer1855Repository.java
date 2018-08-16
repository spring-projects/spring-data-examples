package example.repo;

import example.model.Customer1855;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1855Repository extends CrudRepository<Customer1855, Long> {

	List<Customer1855> findByLastName(String lastName);
}
