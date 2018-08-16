package example.repo;

import example.model.Customer1323;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1323Repository extends CrudRepository<Customer1323, Long> {

	List<Customer1323> findByLastName(String lastName);
}
