package example.repo;

import example.model.Customer1945;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1945Repository extends CrudRepository<Customer1945, Long> {

	List<Customer1945> findByLastName(String lastName);
}
