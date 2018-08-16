package example.repo;

import example.model.Customer1141;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1141Repository extends CrudRepository<Customer1141, Long> {

	List<Customer1141> findByLastName(String lastName);
}
