package example.repo;

import example.model.Customer1478;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1478Repository extends CrudRepository<Customer1478, Long> {

	List<Customer1478> findByLastName(String lastName);
}
