package example.repo;

import example.model.Customer1593;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1593Repository extends CrudRepository<Customer1593, Long> {

	List<Customer1593> findByLastName(String lastName);
}
