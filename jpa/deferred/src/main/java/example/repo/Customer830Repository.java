package example.repo;

import example.model.Customer830;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer830Repository extends CrudRepository<Customer830, Long> {

	List<Customer830> findByLastName(String lastName);
}
