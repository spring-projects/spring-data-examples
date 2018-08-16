package example.repo;

import example.model.Customer1253;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1253Repository extends CrudRepository<Customer1253, Long> {

	List<Customer1253> findByLastName(String lastName);
}
