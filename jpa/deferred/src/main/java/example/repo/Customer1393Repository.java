package example.repo;

import example.model.Customer1393;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1393Repository extends CrudRepository<Customer1393, Long> {

	List<Customer1393> findByLastName(String lastName);
}
