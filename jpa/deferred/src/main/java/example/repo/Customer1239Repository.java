package example.repo;

import example.model.Customer1239;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1239Repository extends CrudRepository<Customer1239, Long> {

	List<Customer1239> findByLastName(String lastName);
}
