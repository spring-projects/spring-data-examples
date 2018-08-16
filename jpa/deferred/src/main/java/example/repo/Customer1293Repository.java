package example.repo;

import example.model.Customer1293;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1293Repository extends CrudRepository<Customer1293, Long> {

	List<Customer1293> findByLastName(String lastName);
}
