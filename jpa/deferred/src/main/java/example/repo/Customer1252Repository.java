package example.repo;

import example.model.Customer1252;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1252Repository extends CrudRepository<Customer1252, Long> {

	List<Customer1252> findByLastName(String lastName);
}
