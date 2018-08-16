package example.repo;

import example.model.Customer1171;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1171Repository extends CrudRepository<Customer1171, Long> {

	List<Customer1171> findByLastName(String lastName);
}
