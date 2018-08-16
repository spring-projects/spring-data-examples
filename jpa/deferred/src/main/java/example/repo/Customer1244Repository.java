package example.repo;

import example.model.Customer1244;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1244Repository extends CrudRepository<Customer1244, Long> {

	List<Customer1244> findByLastName(String lastName);
}
