package example.repo;

import example.model.Customer1525;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1525Repository extends CrudRepository<Customer1525, Long> {

	List<Customer1525> findByLastName(String lastName);
}
