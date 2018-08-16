package example.repo;

import example.model.Customer55;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer55Repository extends CrudRepository<Customer55, Long> {

	List<Customer55> findByLastName(String lastName);
}
