package example.repo;

import example.model.Customer819;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer819Repository extends CrudRepository<Customer819, Long> {

	List<Customer819> findByLastName(String lastName);
}
