package example.repo;

import example.model.Customer1651;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1651Repository extends CrudRepository<Customer1651, Long> {

	List<Customer1651> findByLastName(String lastName);
}
