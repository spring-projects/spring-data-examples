package example.repo;

import example.model.Customer1471;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1471Repository extends CrudRepository<Customer1471, Long> {

	List<Customer1471> findByLastName(String lastName);
}
