package example.repo;

import example.model.Customer1009;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1009Repository extends CrudRepository<Customer1009, Long> {

	List<Customer1009> findByLastName(String lastName);
}
