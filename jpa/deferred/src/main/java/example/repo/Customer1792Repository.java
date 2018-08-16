package example.repo;

import example.model.Customer1792;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1792Repository extends CrudRepository<Customer1792, Long> {

	List<Customer1792> findByLastName(String lastName);
}
