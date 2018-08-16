package example.repo;

import example.model.Customer213;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer213Repository extends CrudRepository<Customer213, Long> {

	List<Customer213> findByLastName(String lastName);
}
