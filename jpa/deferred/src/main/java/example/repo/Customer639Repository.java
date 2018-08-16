package example.repo;

import example.model.Customer639;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer639Repository extends CrudRepository<Customer639, Long> {

	List<Customer639> findByLastName(String lastName);
}
