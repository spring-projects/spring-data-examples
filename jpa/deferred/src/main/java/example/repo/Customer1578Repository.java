package example.repo;

import example.model.Customer1578;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1578Repository extends CrudRepository<Customer1578, Long> {

	List<Customer1578> findByLastName(String lastName);
}
