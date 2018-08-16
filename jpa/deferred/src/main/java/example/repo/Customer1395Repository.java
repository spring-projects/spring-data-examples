package example.repo;

import example.model.Customer1395;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1395Repository extends CrudRepository<Customer1395, Long> {

	List<Customer1395> findByLastName(String lastName);
}
