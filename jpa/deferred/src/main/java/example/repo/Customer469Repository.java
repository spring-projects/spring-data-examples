package example.repo;

import example.model.Customer469;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer469Repository extends CrudRepository<Customer469, Long> {

	List<Customer469> findByLastName(String lastName);
}
