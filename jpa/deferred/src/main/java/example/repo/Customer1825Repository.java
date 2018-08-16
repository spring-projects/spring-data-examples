package example.repo;

import example.model.Customer1825;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1825Repository extends CrudRepository<Customer1825, Long> {

	List<Customer1825> findByLastName(String lastName);
}
