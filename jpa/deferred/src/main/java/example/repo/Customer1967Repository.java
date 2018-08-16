package example.repo;

import example.model.Customer1967;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1967Repository extends CrudRepository<Customer1967, Long> {

	List<Customer1967> findByLastName(String lastName);
}
