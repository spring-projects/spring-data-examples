package example.repo;

import example.model.Customer1384;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1384Repository extends CrudRepository<Customer1384, Long> {

	List<Customer1384> findByLastName(String lastName);
}
