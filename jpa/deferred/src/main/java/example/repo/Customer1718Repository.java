package example.repo;

import example.model.Customer1718;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1718Repository extends CrudRepository<Customer1718, Long> {

	List<Customer1718> findByLastName(String lastName);
}
