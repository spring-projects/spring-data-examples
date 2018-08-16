package example.repo;

import example.model.Customer1013;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1013Repository extends CrudRepository<Customer1013, Long> {

	List<Customer1013> findByLastName(String lastName);
}
