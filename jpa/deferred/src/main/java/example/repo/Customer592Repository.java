package example.repo;

import example.model.Customer592;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer592Repository extends CrudRepository<Customer592, Long> {

	List<Customer592> findByLastName(String lastName);
}
