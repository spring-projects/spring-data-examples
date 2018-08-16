package example.repo;

import example.model.Customer755;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer755Repository extends CrudRepository<Customer755, Long> {

	List<Customer755> findByLastName(String lastName);
}
