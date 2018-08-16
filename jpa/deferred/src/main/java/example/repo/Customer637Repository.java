package example.repo;

import example.model.Customer637;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer637Repository extends CrudRepository<Customer637, Long> {

	List<Customer637> findByLastName(String lastName);
}
