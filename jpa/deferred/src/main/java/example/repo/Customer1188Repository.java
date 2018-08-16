package example.repo;

import example.model.Customer1188;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1188Repository extends CrudRepository<Customer1188, Long> {

	List<Customer1188> findByLastName(String lastName);
}
