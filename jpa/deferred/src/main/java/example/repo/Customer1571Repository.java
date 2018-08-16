package example.repo;

import example.model.Customer1571;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1571Repository extends CrudRepository<Customer1571, Long> {

	List<Customer1571> findByLastName(String lastName);
}
