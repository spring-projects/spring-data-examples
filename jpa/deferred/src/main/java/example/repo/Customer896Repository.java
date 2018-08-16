package example.repo;

import example.model.Customer896;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer896Repository extends CrudRepository<Customer896, Long> {

	List<Customer896> findByLastName(String lastName);
}
