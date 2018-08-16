package example.repo;

import example.model.Customer1575;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1575Repository extends CrudRepository<Customer1575, Long> {

	List<Customer1575> findByLastName(String lastName);
}
