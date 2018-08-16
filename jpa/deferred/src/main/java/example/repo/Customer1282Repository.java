package example.repo;

import example.model.Customer1282;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1282Repository extends CrudRepository<Customer1282, Long> {

	List<Customer1282> findByLastName(String lastName);
}
