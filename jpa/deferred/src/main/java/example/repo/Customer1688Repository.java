package example.repo;

import example.model.Customer1688;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1688Repository extends CrudRepository<Customer1688, Long> {

	List<Customer1688> findByLastName(String lastName);
}
