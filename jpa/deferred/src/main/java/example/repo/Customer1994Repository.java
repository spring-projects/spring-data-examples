package example.repo;

import example.model.Customer1994;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1994Repository extends CrudRepository<Customer1994, Long> {

	List<Customer1994> findByLastName(String lastName);
}
