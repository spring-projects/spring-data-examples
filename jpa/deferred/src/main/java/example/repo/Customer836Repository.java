package example.repo;

import example.model.Customer836;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer836Repository extends CrudRepository<Customer836, Long> {

	List<Customer836> findByLastName(String lastName);
}
