package example.repo;

import example.model.Customer357;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer357Repository extends CrudRepository<Customer357, Long> {

	List<Customer357> findByLastName(String lastName);
}
