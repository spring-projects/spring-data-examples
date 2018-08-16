package example.repo;

import example.model.Customer111;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer111Repository extends CrudRepository<Customer111, Long> {

	List<Customer111> findByLastName(String lastName);
}
