package example.repo;

import example.model.Customer261;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer261Repository extends CrudRepository<Customer261, Long> {

	List<Customer261> findByLastName(String lastName);
}
