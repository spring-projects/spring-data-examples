package example.repo;

import example.model.Customer1481;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1481Repository extends CrudRepository<Customer1481, Long> {

	List<Customer1481> findByLastName(String lastName);
}
