package example.repo;

import example.model.Customer1682;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1682Repository extends CrudRepository<Customer1682, Long> {

	List<Customer1682> findByLastName(String lastName);
}
