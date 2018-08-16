package example.repo;

import example.model.Customer1001;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1001Repository extends CrudRepository<Customer1001, Long> {

	List<Customer1001> findByLastName(String lastName);
}
