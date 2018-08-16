package example.repo;

import example.model.Customer1655;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1655Repository extends CrudRepository<Customer1655, Long> {

	List<Customer1655> findByLastName(String lastName);
}
