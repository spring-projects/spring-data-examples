package example.repo;

import example.model.Customer590;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer590Repository extends CrudRepository<Customer590, Long> {

	List<Customer590> findByLastName(String lastName);
}
