package example.repo;

import example.model.Customer572;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer572Repository extends CrudRepository<Customer572, Long> {

	List<Customer572> findByLastName(String lastName);
}
