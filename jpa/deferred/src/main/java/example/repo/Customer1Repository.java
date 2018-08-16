package example.repo;

import example.model.Customer1;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1Repository extends CrudRepository<Customer1, Long> {

	List<Customer1> findByLastName(String lastName);
}
