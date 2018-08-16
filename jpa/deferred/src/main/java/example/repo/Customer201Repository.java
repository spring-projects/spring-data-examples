package example.repo;

import example.model.Customer201;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer201Repository extends CrudRepository<Customer201, Long> {

	List<Customer201> findByLastName(String lastName);
}
