package example.repo;

import example.model.Customer1552;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1552Repository extends CrudRepository<Customer1552, Long> {

	List<Customer1552> findByLastName(String lastName);
}
