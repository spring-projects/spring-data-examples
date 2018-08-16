package example.repo;

import example.model.Customer1760;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1760Repository extends CrudRepository<Customer1760, Long> {

	List<Customer1760> findByLastName(String lastName);
}
