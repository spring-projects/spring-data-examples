package example.repo;

import example.model.Customer1640;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1640Repository extends CrudRepository<Customer1640, Long> {

	List<Customer1640> findByLastName(String lastName);
}
