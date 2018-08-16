package example.repo;

import example.model.Customer585;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer585Repository extends CrudRepository<Customer585, Long> {

	List<Customer585> findByLastName(String lastName);
}
