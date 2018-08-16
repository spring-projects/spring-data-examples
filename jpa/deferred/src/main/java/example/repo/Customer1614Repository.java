package example.repo;

import example.model.Customer1614;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1614Repository extends CrudRepository<Customer1614, Long> {

	List<Customer1614> findByLastName(String lastName);
}
