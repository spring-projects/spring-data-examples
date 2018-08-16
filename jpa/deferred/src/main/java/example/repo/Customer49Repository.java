package example.repo;

import example.model.Customer49;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer49Repository extends CrudRepository<Customer49, Long> {

	List<Customer49> findByLastName(String lastName);
}
