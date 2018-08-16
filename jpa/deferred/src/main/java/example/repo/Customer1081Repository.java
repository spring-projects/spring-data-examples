package example.repo;

import example.model.Customer1081;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1081Repository extends CrudRepository<Customer1081, Long> {

	List<Customer1081> findByLastName(String lastName);
}
