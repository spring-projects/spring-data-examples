package example.repo;

import example.model.Customer129;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer129Repository extends CrudRepository<Customer129, Long> {

	List<Customer129> findByLastName(String lastName);
}
