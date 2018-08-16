package example.repo;

import example.model.Customer1195;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1195Repository extends CrudRepository<Customer1195, Long> {

	List<Customer1195> findByLastName(String lastName);
}
