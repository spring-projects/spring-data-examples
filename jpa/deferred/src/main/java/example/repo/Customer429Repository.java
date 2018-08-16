package example.repo;

import example.model.Customer429;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer429Repository extends CrudRepository<Customer429, Long> {

	List<Customer429> findByLastName(String lastName);
}
