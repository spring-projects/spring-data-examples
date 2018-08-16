package example.repo;

import example.model.Customer1771;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1771Repository extends CrudRepository<Customer1771, Long> {

	List<Customer1771> findByLastName(String lastName);
}
