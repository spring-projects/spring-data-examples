package example.repo;

import example.model.Customer1163;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1163Repository extends CrudRepository<Customer1163, Long> {

	List<Customer1163> findByLastName(String lastName);
}
