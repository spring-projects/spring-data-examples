package example.repo;

import example.model.Customer1354;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1354Repository extends CrudRepository<Customer1354, Long> {

	List<Customer1354> findByLastName(String lastName);
}
