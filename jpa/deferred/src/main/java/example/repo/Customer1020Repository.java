package example.repo;

import example.model.Customer1020;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1020Repository extends CrudRepository<Customer1020, Long> {

	List<Customer1020> findByLastName(String lastName);
}
