package example.repo;

import example.model.Customer1590;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1590Repository extends CrudRepository<Customer1590, Long> {

	List<Customer1590> findByLastName(String lastName);
}
