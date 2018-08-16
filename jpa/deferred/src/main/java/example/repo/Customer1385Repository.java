package example.repo;

import example.model.Customer1385;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1385Repository extends CrudRepository<Customer1385, Long> {

	List<Customer1385> findByLastName(String lastName);
}
