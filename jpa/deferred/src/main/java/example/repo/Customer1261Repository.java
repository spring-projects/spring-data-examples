package example.repo;

import example.model.Customer1261;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1261Repository extends CrudRepository<Customer1261, Long> {

	List<Customer1261> findByLastName(String lastName);
}
