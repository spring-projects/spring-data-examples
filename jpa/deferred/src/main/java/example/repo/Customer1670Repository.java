package example.repo;

import example.model.Customer1670;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1670Repository extends CrudRepository<Customer1670, Long> {

	List<Customer1670> findByLastName(String lastName);
}
