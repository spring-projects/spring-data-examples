package example.repo;

import example.model.Customer1245;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1245Repository extends CrudRepository<Customer1245, Long> {

	List<Customer1245> findByLastName(String lastName);
}
