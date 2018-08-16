package example.repo;

import example.model.Customer1488;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1488Repository extends CrudRepository<Customer1488, Long> {

	List<Customer1488> findByLastName(String lastName);
}
