package example.repo;

import example.model.Customer1779;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1779Repository extends CrudRepository<Customer1779, Long> {

	List<Customer1779> findByLastName(String lastName);
}
