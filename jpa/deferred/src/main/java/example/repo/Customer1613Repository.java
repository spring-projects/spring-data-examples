package example.repo;

import example.model.Customer1613;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1613Repository extends CrudRepository<Customer1613, Long> {

	List<Customer1613> findByLastName(String lastName);
}
