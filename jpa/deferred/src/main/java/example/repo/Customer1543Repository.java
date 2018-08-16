package example.repo;

import example.model.Customer1543;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1543Repository extends CrudRepository<Customer1543, Long> {

	List<Customer1543> findByLastName(String lastName);
}
