package example.repo;

import example.model.Customer329;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer329Repository extends CrudRepository<Customer329, Long> {

	List<Customer329> findByLastName(String lastName);
}
