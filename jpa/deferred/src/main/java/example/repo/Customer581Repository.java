package example.repo;

import example.model.Customer581;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer581Repository extends CrudRepository<Customer581, Long> {

	List<Customer581> findByLastName(String lastName);
}
