package example.repo;

import example.model.Customer1231;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1231Repository extends CrudRepository<Customer1231, Long> {

	List<Customer1231> findByLastName(String lastName);
}
