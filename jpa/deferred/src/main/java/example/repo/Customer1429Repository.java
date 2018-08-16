package example.repo;

import example.model.Customer1429;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1429Repository extends CrudRepository<Customer1429, Long> {

	List<Customer1429> findByLastName(String lastName);
}
