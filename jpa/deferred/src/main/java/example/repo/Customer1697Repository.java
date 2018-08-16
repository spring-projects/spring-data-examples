package example.repo;

import example.model.Customer1697;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1697Repository extends CrudRepository<Customer1697, Long> {

	List<Customer1697> findByLastName(String lastName);
}
