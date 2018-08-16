package example.repo;

import example.model.Customer274;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer274Repository extends CrudRepository<Customer274, Long> {

	List<Customer274> findByLastName(String lastName);
}
