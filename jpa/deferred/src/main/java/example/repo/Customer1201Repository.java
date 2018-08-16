package example.repo;

import example.model.Customer1201;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1201Repository extends CrudRepository<Customer1201, Long> {

	List<Customer1201> findByLastName(String lastName);
}
