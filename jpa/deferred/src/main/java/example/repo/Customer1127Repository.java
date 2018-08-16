package example.repo;

import example.model.Customer1127;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1127Repository extends CrudRepository<Customer1127, Long> {

	List<Customer1127> findByLastName(String lastName);
}
