package example.repo;

import example.model.Customer924;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer924Repository extends CrudRepository<Customer924, Long> {

	List<Customer924> findByLastName(String lastName);
}
