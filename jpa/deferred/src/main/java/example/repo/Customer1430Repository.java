package example.repo;

import example.model.Customer1430;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1430Repository extends CrudRepository<Customer1430, Long> {

	List<Customer1430> findByLastName(String lastName);
}
