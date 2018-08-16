package example.repo;

import example.model.Customer1956;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1956Repository extends CrudRepository<Customer1956, Long> {

	List<Customer1956> findByLastName(String lastName);
}
