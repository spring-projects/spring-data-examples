package example.repo;

import example.model.Customer1665;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1665Repository extends CrudRepository<Customer1665, Long> {

	List<Customer1665> findByLastName(String lastName);
}
