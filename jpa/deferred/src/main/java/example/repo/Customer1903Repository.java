package example.repo;

import example.model.Customer1903;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1903Repository extends CrudRepository<Customer1903, Long> {

	List<Customer1903> findByLastName(String lastName);
}
