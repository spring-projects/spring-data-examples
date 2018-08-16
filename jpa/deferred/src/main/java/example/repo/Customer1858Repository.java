package example.repo;

import example.model.Customer1858;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1858Repository extends CrudRepository<Customer1858, Long> {

	List<Customer1858> findByLastName(String lastName);
}
