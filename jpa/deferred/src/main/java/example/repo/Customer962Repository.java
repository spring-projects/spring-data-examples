package example.repo;

import example.model.Customer962;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer962Repository extends CrudRepository<Customer962, Long> {

	List<Customer962> findByLastName(String lastName);
}
