package example.repo;

import example.model.Customer533;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer533Repository extends CrudRepository<Customer533, Long> {

	List<Customer533> findByLastName(String lastName);
}
