package example.repo;

import example.model.Customer1798;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1798Repository extends CrudRepository<Customer1798, Long> {

	List<Customer1798> findByLastName(String lastName);
}
