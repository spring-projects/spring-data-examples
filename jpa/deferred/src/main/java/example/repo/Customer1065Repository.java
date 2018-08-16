package example.repo;

import example.model.Customer1065;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1065Repository extends CrudRepository<Customer1065, Long> {

	List<Customer1065> findByLastName(String lastName);
}
