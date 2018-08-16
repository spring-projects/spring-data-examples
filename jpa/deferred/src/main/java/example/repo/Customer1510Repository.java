package example.repo;

import example.model.Customer1510;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1510Repository extends CrudRepository<Customer1510, Long> {

	List<Customer1510> findByLastName(String lastName);
}
