package example.repo;

import example.model.Customer507;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer507Repository extends CrudRepository<Customer507, Long> {

	List<Customer507> findByLastName(String lastName);
}
