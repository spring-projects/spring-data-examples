package example.repo;

import example.model.Customer1399;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1399Repository extends CrudRepository<Customer1399, Long> {

	List<Customer1399> findByLastName(String lastName);
}
