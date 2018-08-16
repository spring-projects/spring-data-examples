package example.repo;

import example.model.Customer1821;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1821Repository extends CrudRepository<Customer1821, Long> {

	List<Customer1821> findByLastName(String lastName);
}
