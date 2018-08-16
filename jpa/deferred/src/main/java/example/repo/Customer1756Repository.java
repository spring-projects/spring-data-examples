package example.repo;

import example.model.Customer1756;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1756Repository extends CrudRepository<Customer1756, Long> {

	List<Customer1756> findByLastName(String lastName);
}
