package example.repo;

import example.model.Customer1906;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1906Repository extends CrudRepository<Customer1906, Long> {

	List<Customer1906> findByLastName(String lastName);
}
