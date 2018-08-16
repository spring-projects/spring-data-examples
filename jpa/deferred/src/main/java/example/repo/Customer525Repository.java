package example.repo;

import example.model.Customer525;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer525Repository extends CrudRepository<Customer525, Long> {

	List<Customer525> findByLastName(String lastName);
}
