package example.repo;

import example.model.Customer915;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer915Repository extends CrudRepository<Customer915, Long> {

	List<Customer915> findByLastName(String lastName);
}
