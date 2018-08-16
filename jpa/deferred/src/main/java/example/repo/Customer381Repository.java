package example.repo;

import example.model.Customer381;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer381Repository extends CrudRepository<Customer381, Long> {

	List<Customer381> findByLastName(String lastName);
}
