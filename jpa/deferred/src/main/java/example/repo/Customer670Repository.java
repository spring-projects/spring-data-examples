package example.repo;

import example.model.Customer670;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer670Repository extends CrudRepository<Customer670, Long> {

	List<Customer670> findByLastName(String lastName);
}
