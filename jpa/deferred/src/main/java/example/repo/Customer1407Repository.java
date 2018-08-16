package example.repo;

import example.model.Customer1407;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1407Repository extends CrudRepository<Customer1407, Long> {

	List<Customer1407> findByLastName(String lastName);
}
