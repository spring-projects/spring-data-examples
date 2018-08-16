package example.repo;

import example.model.Customer1641;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1641Repository extends CrudRepository<Customer1641, Long> {

	List<Customer1641> findByLastName(String lastName);
}
