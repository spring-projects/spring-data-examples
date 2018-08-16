package example.repo;

import example.model.Customer1443;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1443Repository extends CrudRepository<Customer1443, Long> {

	List<Customer1443> findByLastName(String lastName);
}
