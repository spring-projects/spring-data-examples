package example.repo;

import example.model.Customer1411;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1411Repository extends CrudRepository<Customer1411, Long> {

	List<Customer1411> findByLastName(String lastName);
}
