package example.repo;

import example.model.Customer1529;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1529Repository extends CrudRepository<Customer1529, Long> {

	List<Customer1529> findByLastName(String lastName);
}
