package example.repo;

import example.model.Customer1006;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1006Repository extends CrudRepository<Customer1006, Long> {

	List<Customer1006> findByLastName(String lastName);
}
