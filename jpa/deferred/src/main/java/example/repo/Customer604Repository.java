package example.repo;

import example.model.Customer604;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer604Repository extends CrudRepository<Customer604, Long> {

	List<Customer604> findByLastName(String lastName);
}
