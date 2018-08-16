package example.repo;

import example.model.Customer704;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer704Repository extends CrudRepository<Customer704, Long> {

	List<Customer704> findByLastName(String lastName);
}
