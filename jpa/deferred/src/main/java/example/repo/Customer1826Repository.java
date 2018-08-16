package example.repo;

import example.model.Customer1826;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1826Repository extends CrudRepository<Customer1826, Long> {

	List<Customer1826> findByLastName(String lastName);
}
