package example.repo;

import example.model.Customer824;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer824Repository extends CrudRepository<Customer824, Long> {

	List<Customer824> findByLastName(String lastName);
}
