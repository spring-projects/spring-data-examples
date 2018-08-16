package example.repo;

import example.model.Customer1839;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1839Repository extends CrudRepository<Customer1839, Long> {

	List<Customer1839> findByLastName(String lastName);
}
