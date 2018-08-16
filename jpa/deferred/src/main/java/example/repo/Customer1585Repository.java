package example.repo;

import example.model.Customer1585;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1585Repository extends CrudRepository<Customer1585, Long> {

	List<Customer1585> findByLastName(String lastName);
}
