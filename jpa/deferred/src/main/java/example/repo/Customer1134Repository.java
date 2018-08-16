package example.repo;

import example.model.Customer1134;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1134Repository extends CrudRepository<Customer1134, Long> {

	List<Customer1134> findByLastName(String lastName);
}
