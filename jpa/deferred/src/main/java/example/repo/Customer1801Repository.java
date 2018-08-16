package example.repo;

import example.model.Customer1801;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1801Repository extends CrudRepository<Customer1801, Long> {

	List<Customer1801> findByLastName(String lastName);
}
