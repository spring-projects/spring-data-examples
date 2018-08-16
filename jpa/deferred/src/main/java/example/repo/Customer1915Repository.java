package example.repo;

import example.model.Customer1915;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1915Repository extends CrudRepository<Customer1915, Long> {

	List<Customer1915> findByLastName(String lastName);
}
