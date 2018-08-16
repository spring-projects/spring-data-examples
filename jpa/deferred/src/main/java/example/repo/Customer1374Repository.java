package example.repo;

import example.model.Customer1374;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1374Repository extends CrudRepository<Customer1374, Long> {

	List<Customer1374> findByLastName(String lastName);
}
