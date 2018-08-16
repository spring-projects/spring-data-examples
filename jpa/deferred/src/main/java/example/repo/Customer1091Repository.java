package example.repo;

import example.model.Customer1091;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1091Repository extends CrudRepository<Customer1091, Long> {

	List<Customer1091> findByLastName(String lastName);
}
