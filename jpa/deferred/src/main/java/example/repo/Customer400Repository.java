package example.repo;

import example.model.Customer400;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer400Repository extends CrudRepository<Customer400, Long> {

	List<Customer400> findByLastName(String lastName);
}
