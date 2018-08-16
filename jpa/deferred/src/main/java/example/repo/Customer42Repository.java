package example.repo;

import example.model.Customer42;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer42Repository extends CrudRepository<Customer42, Long> {

	List<Customer42> findByLastName(String lastName);
}
