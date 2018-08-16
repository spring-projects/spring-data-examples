package example.repo;

import example.model.Customer839;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer839Repository extends CrudRepository<Customer839, Long> {

	List<Customer839> findByLastName(String lastName);
}
