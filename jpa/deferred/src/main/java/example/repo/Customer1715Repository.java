package example.repo;

import example.model.Customer1715;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1715Repository extends CrudRepository<Customer1715, Long> {

	List<Customer1715> findByLastName(String lastName);
}
