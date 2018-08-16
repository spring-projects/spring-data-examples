package example.repo;

import example.model.Customer1199;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1199Repository extends CrudRepository<Customer1199, Long> {

	List<Customer1199> findByLastName(String lastName);
}
