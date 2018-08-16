package example.repo;

import example.model.Customer498;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer498Repository extends CrudRepository<Customer498, Long> {

	List<Customer498> findByLastName(String lastName);
}
