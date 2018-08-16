package example.repo;

import example.model.Customer729;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer729Repository extends CrudRepository<Customer729, Long> {

	List<Customer729> findByLastName(String lastName);
}
