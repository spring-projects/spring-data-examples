package example.repo;

import example.model.Customer1884;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1884Repository extends CrudRepository<Customer1884, Long> {

	List<Customer1884> findByLastName(String lastName);
}
