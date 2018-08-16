package example.repo;

import example.model.Customer212;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer212Repository extends CrudRepository<Customer212, Long> {

	List<Customer212> findByLastName(String lastName);
}
