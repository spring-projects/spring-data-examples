package example.repo;

import example.model.Customer18;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer18Repository extends CrudRepository<Customer18, Long> {

	List<Customer18> findByLastName(String lastName);
}
