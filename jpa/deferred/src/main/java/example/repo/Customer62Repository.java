package example.repo;

import example.model.Customer62;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer62Repository extends CrudRepository<Customer62, Long> {

	List<Customer62> findByLastName(String lastName);
}
