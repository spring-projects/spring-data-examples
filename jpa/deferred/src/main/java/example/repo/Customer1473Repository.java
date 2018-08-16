package example.repo;

import example.model.Customer1473;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1473Repository extends CrudRepository<Customer1473, Long> {

	List<Customer1473> findByLastName(String lastName);
}
