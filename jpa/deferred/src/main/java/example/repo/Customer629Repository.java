package example.repo;

import example.model.Customer629;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer629Repository extends CrudRepository<Customer629, Long> {

	List<Customer629> findByLastName(String lastName);
}
