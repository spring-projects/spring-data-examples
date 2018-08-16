package example.repo;

import example.model.Customer234;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer234Repository extends CrudRepository<Customer234, Long> {

	List<Customer234> findByLastName(String lastName);
}
