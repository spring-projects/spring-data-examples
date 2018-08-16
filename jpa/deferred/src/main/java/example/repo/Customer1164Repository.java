package example.repo;

import example.model.Customer1164;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1164Repository extends CrudRepository<Customer1164, Long> {

	List<Customer1164> findByLastName(String lastName);
}
