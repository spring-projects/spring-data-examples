package example.repo;

import example.model.Customer1018;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1018Repository extends CrudRepository<Customer1018, Long> {

	List<Customer1018> findByLastName(String lastName);
}
