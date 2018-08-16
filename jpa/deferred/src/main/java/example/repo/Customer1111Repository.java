package example.repo;

import example.model.Customer1111;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1111Repository extends CrudRepository<Customer1111, Long> {

	List<Customer1111> findByLastName(String lastName);
}
