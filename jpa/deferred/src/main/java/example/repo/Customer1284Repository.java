package example.repo;

import example.model.Customer1284;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1284Repository extends CrudRepository<Customer1284, Long> {

	List<Customer1284> findByLastName(String lastName);
}
