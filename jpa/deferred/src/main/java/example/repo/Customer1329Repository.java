package example.repo;

import example.model.Customer1329;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1329Repository extends CrudRepository<Customer1329, Long> {

	List<Customer1329> findByLastName(String lastName);
}
