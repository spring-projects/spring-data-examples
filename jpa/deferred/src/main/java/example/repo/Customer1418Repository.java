package example.repo;

import example.model.Customer1418;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1418Repository extends CrudRepository<Customer1418, Long> {

	List<Customer1418> findByLastName(String lastName);
}
