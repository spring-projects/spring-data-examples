package example.repo;

import example.model.Customer1497;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1497Repository extends CrudRepository<Customer1497, Long> {

	List<Customer1497> findByLastName(String lastName);
}
