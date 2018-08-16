package example.repo;

import example.model.Customer1116;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1116Repository extends CrudRepository<Customer1116, Long> {

	List<Customer1116> findByLastName(String lastName);
}
