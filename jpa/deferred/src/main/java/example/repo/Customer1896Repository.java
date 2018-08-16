package example.repo;

import example.model.Customer1896;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1896Repository extends CrudRepository<Customer1896, Long> {

	List<Customer1896> findByLastName(String lastName);
}
