package example.repo;

import example.model.Customer1928;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1928Repository extends CrudRepository<Customer1928, Long> {

	List<Customer1928> findByLastName(String lastName);
}
