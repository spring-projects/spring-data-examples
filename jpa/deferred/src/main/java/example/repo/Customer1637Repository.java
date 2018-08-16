package example.repo;

import example.model.Customer1637;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1637Repository extends CrudRepository<Customer1637, Long> {

	List<Customer1637> findByLastName(String lastName);
}
