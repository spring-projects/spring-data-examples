package example.repo;

import example.model.Customer552;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer552Repository extends CrudRepository<Customer552, Long> {

	List<Customer552> findByLastName(String lastName);
}
