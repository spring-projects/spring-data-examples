package example.repo;

import example.model.Customer1438;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1438Repository extends CrudRepository<Customer1438, Long> {

	List<Customer1438> findByLastName(String lastName);
}
