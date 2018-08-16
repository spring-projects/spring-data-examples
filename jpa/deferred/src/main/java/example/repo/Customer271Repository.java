package example.repo;

import example.model.Customer271;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer271Repository extends CrudRepository<Customer271, Long> {

	List<Customer271> findByLastName(String lastName);
}
