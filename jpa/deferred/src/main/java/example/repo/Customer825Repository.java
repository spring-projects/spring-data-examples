package example.repo;

import example.model.Customer825;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer825Repository extends CrudRepository<Customer825, Long> {

	List<Customer825> findByLastName(String lastName);
}
