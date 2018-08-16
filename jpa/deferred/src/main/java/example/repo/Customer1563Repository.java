package example.repo;

import example.model.Customer1563;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1563Repository extends CrudRepository<Customer1563, Long> {

	List<Customer1563> findByLastName(String lastName);
}
