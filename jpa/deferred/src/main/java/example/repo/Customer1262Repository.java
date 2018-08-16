package example.repo;

import example.model.Customer1262;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1262Repository extends CrudRepository<Customer1262, Long> {

	List<Customer1262> findByLastName(String lastName);
}
