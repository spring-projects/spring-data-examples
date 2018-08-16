package example.repo;

import example.model.Customer1591;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1591Repository extends CrudRepository<Customer1591, Long> {

	List<Customer1591> findByLastName(String lastName);
}
