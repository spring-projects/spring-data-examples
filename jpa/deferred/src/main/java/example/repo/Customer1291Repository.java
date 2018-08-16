package example.repo;

import example.model.Customer1291;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1291Repository extends CrudRepository<Customer1291, Long> {

	List<Customer1291> findByLastName(String lastName);
}
