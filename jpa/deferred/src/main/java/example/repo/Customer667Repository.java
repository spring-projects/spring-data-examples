package example.repo;

import example.model.Customer667;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer667Repository extends CrudRepository<Customer667, Long> {

	List<Customer667> findByLastName(String lastName);
}
