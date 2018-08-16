package example.repo;

import example.model.Customer1547;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1547Repository extends CrudRepository<Customer1547, Long> {

	List<Customer1547> findByLastName(String lastName);
}
