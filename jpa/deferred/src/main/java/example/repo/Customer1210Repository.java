package example.repo;

import example.model.Customer1210;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1210Repository extends CrudRepository<Customer1210, Long> {

	List<Customer1210> findByLastName(String lastName);
}
