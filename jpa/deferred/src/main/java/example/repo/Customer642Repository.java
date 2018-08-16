package example.repo;

import example.model.Customer642;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer642Repository extends CrudRepository<Customer642, Long> {

	List<Customer642> findByLastName(String lastName);
}
