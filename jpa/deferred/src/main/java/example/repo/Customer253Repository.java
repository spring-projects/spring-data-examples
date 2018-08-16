package example.repo;

import example.model.Customer253;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer253Repository extends CrudRepository<Customer253, Long> {

	List<Customer253> findByLastName(String lastName);
}
