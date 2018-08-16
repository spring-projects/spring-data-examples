package example.repo;

import example.model.Customer1998;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1998Repository extends CrudRepository<Customer1998, Long> {

	List<Customer1998> findByLastName(String lastName);
}
