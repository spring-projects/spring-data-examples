package example.repo;

import example.model.Customer1044;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1044Repository extends CrudRepository<Customer1044, Long> {

	List<Customer1044> findByLastName(String lastName);
}
