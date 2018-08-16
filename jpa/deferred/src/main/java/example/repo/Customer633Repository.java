package example.repo;

import example.model.Customer633;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer633Repository extends CrudRepository<Customer633, Long> {

	List<Customer633> findByLastName(String lastName);
}
