package example.repo;

import example.model.Customer1246;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1246Repository extends CrudRepository<Customer1246, Long> {

	List<Customer1246> findByLastName(String lastName);
}
