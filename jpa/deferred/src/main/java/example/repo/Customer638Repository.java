package example.repo;

import example.model.Customer638;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer638Repository extends CrudRepository<Customer638, Long> {

	List<Customer638> findByLastName(String lastName);
}
