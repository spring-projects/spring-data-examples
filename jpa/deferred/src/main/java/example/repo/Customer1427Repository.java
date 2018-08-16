package example.repo;

import example.model.Customer1427;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1427Repository extends CrudRepository<Customer1427, Long> {

	List<Customer1427> findByLastName(String lastName);
}
