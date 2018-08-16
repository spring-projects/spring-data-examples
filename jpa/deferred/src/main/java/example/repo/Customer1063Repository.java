package example.repo;

import example.model.Customer1063;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1063Repository extends CrudRepository<Customer1063, Long> {

	List<Customer1063> findByLastName(String lastName);
}
