package example.repo;

import example.model.Customer1106;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1106Repository extends CrudRepository<Customer1106, Long> {

	List<Customer1106> findByLastName(String lastName);
}
