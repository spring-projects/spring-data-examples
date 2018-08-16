package example.repo;

import example.model.Customer1404;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1404Repository extends CrudRepository<Customer1404, Long> {

	List<Customer1404> findByLastName(String lastName);
}
