package example.repo;

import example.model.Customer1608;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1608Repository extends CrudRepository<Customer1608, Long> {

	List<Customer1608> findByLastName(String lastName);
}
