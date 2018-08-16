package example.repo;

import example.model.Customer309;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer309Repository extends CrudRepository<Customer309, Long> {

	List<Customer309> findByLastName(String lastName);
}
