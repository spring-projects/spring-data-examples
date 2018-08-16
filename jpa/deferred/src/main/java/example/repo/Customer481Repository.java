package example.repo;

import example.model.Customer481;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer481Repository extends CrudRepository<Customer481, Long> {

	List<Customer481> findByLastName(String lastName);
}
