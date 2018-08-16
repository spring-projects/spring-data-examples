package example.repo;

import example.model.Customer1462;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1462Repository extends CrudRepository<Customer1462, Long> {

	List<Customer1462> findByLastName(String lastName);
}
