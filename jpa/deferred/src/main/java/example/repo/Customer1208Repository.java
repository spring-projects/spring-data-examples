package example.repo;

import example.model.Customer1208;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1208Repository extends CrudRepository<Customer1208, Long> {

	List<Customer1208> findByLastName(String lastName);
}
