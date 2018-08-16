package example.repo;

import example.model.Customer1661;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1661Repository extends CrudRepository<Customer1661, Long> {

	List<Customer1661> findByLastName(String lastName);
}
