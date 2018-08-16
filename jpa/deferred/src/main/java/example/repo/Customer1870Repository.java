package example.repo;

import example.model.Customer1870;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1870Repository extends CrudRepository<Customer1870, Long> {

	List<Customer1870> findByLastName(String lastName);
}
