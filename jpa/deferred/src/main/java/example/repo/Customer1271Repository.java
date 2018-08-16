package example.repo;

import example.model.Customer1271;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1271Repository extends CrudRepository<Customer1271, Long> {

	List<Customer1271> findByLastName(String lastName);
}
