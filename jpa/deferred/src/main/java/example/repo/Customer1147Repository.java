package example.repo;

import example.model.Customer1147;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1147Repository extends CrudRepository<Customer1147, Long> {

	List<Customer1147> findByLastName(String lastName);
}
