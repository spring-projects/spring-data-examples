package example.repo;

import example.model.Customer1023;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1023Repository extends CrudRepository<Customer1023, Long> {

	List<Customer1023> findByLastName(String lastName);
}
