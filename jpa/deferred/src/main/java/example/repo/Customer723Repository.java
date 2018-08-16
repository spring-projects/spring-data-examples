package example.repo;

import example.model.Customer723;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer723Repository extends CrudRepository<Customer723, Long> {

	List<Customer723> findByLastName(String lastName);
}
