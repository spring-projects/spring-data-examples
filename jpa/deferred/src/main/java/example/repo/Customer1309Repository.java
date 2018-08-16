package example.repo;

import example.model.Customer1309;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1309Repository extends CrudRepository<Customer1309, Long> {

	List<Customer1309> findByLastName(String lastName);
}
