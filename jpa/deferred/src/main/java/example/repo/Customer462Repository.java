package example.repo;

import example.model.Customer462;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer462Repository extends CrudRepository<Customer462, Long> {

	List<Customer462> findByLastName(String lastName);
}
