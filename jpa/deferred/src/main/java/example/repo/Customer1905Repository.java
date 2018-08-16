package example.repo;

import example.model.Customer1905;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1905Repository extends CrudRepository<Customer1905, Long> {

	List<Customer1905> findByLastName(String lastName);
}
