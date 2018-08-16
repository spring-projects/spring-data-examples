package example.repo;

import example.model.Customer573;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer573Repository extends CrudRepository<Customer573, Long> {

	List<Customer573> findByLastName(String lastName);
}
