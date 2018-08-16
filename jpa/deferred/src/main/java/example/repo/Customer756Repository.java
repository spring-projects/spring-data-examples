package example.repo;

import example.model.Customer756;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer756Repository extends CrudRepository<Customer756, Long> {

	List<Customer756> findByLastName(String lastName);
}
