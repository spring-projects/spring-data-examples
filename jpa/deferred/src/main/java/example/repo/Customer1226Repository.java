package example.repo;

import example.model.Customer1226;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1226Repository extends CrudRepository<Customer1226, Long> {

	List<Customer1226> findByLastName(String lastName);
}
