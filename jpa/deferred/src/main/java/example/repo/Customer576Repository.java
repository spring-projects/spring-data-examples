package example.repo;

import example.model.Customer576;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer576Repository extends CrudRepository<Customer576, Long> {

	List<Customer576> findByLastName(String lastName);
}
