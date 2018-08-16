package example.repo;

import example.model.Customer712;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer712Repository extends CrudRepository<Customer712, Long> {

	List<Customer712> findByLastName(String lastName);
}
