package example.repo;

import example.model.Customer436;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer436Repository extends CrudRepository<Customer436, Long> {

	List<Customer436> findByLastName(String lastName);
}
