package example.repo;

import example.model.Customer1546;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1546Repository extends CrudRepository<Customer1546, Long> {

	List<Customer1546> findByLastName(String lastName);
}
