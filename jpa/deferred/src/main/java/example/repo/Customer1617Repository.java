package example.repo;

import example.model.Customer1617;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1617Repository extends CrudRepository<Customer1617, Long> {

	List<Customer1617> findByLastName(String lastName);
}
