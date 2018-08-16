package example.repo;

import example.model.Customer48;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer48Repository extends CrudRepository<Customer48, Long> {

	List<Customer48> findByLastName(String lastName);
}
