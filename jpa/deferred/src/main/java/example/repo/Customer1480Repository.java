package example.repo;

import example.model.Customer1480;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1480Repository extends CrudRepository<Customer1480, Long> {

	List<Customer1480> findByLastName(String lastName);
}
