package example.repo;

import example.model.Customer211;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer211Repository extends CrudRepository<Customer211, Long> {

	List<Customer211> findByLastName(String lastName);
}
