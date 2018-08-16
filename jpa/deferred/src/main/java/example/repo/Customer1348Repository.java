package example.repo;

import example.model.Customer1348;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1348Repository extends CrudRepository<Customer1348, Long> {

	List<Customer1348> findByLastName(String lastName);
}
