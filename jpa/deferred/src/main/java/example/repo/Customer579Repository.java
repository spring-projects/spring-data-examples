package example.repo;

import example.model.Customer579;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer579Repository extends CrudRepository<Customer579, Long> {

	List<Customer579> findByLastName(String lastName);
}
