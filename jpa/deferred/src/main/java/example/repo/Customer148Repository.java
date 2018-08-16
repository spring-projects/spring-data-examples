package example.repo;

import example.model.Customer148;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer148Repository extends CrudRepository<Customer148, Long> {

	List<Customer148> findByLastName(String lastName);
}
