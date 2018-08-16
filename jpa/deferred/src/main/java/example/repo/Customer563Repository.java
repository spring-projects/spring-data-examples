package example.repo;

import example.model.Customer563;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer563Repository extends CrudRepository<Customer563, Long> {

	List<Customer563> findByLastName(String lastName);
}
