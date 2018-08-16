package example.repo;

import example.model.Customer1572;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1572Repository extends CrudRepository<Customer1572, Long> {

	List<Customer1572> findByLastName(String lastName);
}
