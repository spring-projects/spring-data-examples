package example.repo;

import example.model.Customer1105;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1105Repository extends CrudRepository<Customer1105, Long> {

	List<Customer1105> findByLastName(String lastName);
}
