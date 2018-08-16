package example.repo;

import example.model.Customer1084;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1084Repository extends CrudRepository<Customer1084, Long> {

	List<Customer1084> findByLastName(String lastName);
}
