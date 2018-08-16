package example.repo;

import example.model.Customer246;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer246Repository extends CrudRepository<Customer246, Long> {

	List<Customer246> findByLastName(String lastName);
}
