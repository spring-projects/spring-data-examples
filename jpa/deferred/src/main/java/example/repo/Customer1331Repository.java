package example.repo;

import example.model.Customer1331;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1331Repository extends CrudRepository<Customer1331, Long> {

	List<Customer1331> findByLastName(String lastName);
}
