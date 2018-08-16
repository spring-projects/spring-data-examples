package example.repo;

import example.model.Customer623;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer623Repository extends CrudRepository<Customer623, Long> {

	List<Customer623> findByLastName(String lastName);
}
