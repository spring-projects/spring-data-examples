package example.repo;

import example.model.Customer418;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer418Repository extends CrudRepository<Customer418, Long> {

	List<Customer418> findByLastName(String lastName);
}
