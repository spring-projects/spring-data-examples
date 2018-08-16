package example.repo;

import example.model.Customer1554;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1554Repository extends CrudRepository<Customer1554, Long> {

	List<Customer1554> findByLastName(String lastName);
}
