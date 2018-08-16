package example.repo;

import example.model.Customer1099;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1099Repository extends CrudRepository<Customer1099, Long> {

	List<Customer1099> findByLastName(String lastName);
}
