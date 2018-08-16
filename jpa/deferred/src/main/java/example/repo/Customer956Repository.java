package example.repo;

import example.model.Customer956;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer956Repository extends CrudRepository<Customer956, Long> {

	List<Customer956> findByLastName(String lastName);
}
