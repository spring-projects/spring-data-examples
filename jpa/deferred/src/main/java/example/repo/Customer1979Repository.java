package example.repo;

import example.model.Customer1979;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1979Repository extends CrudRepository<Customer1979, Long> {

	List<Customer1979> findByLastName(String lastName);
}
