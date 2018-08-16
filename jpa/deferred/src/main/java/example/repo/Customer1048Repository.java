package example.repo;

import example.model.Customer1048;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1048Repository extends CrudRepository<Customer1048, Long> {

	List<Customer1048> findByLastName(String lastName);
}
