package example.repo;

import example.model.Customer178;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer178Repository extends CrudRepository<Customer178, Long> {

	List<Customer178> findByLastName(String lastName);
}
