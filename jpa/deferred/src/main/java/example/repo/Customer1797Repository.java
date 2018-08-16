package example.repo;

import example.model.Customer1797;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1797Repository extends CrudRepository<Customer1797, Long> {

	List<Customer1797> findByLastName(String lastName);
}
