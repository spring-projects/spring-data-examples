package example.repo;

import example.model.Customer1211;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1211Repository extends CrudRepository<Customer1211, Long> {

	List<Customer1211> findByLastName(String lastName);
}
