package example.repo;

import example.model.Customer1003;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1003Repository extends CrudRepository<Customer1003, Long> {

	List<Customer1003> findByLastName(String lastName);
}
