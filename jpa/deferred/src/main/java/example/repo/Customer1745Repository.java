package example.repo;

import example.model.Customer1745;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1745Repository extends CrudRepository<Customer1745, Long> {

	List<Customer1745> findByLastName(String lastName);
}
