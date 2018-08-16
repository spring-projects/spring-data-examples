package example.repo;

import example.model.Customer388;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer388Repository extends CrudRepository<Customer388, Long> {

	List<Customer388> findByLastName(String lastName);
}
