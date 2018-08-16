package example.repo;

import example.model.Customer163;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer163Repository extends CrudRepository<Customer163, Long> {

	List<Customer163> findByLastName(String lastName);
}
