package example.repo;

import example.model.Customer109;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer109Repository extends CrudRepository<Customer109, Long> {

	List<Customer109> findByLastName(String lastName);
}
