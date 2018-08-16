package example.repo;

import example.model.Customer1962;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1962Repository extends CrudRepository<Customer1962, Long> {

	List<Customer1962> findByLastName(String lastName);
}
