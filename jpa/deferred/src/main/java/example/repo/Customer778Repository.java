package example.repo;

import example.model.Customer778;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer778Repository extends CrudRepository<Customer778, Long> {

	List<Customer778> findByLastName(String lastName);
}
