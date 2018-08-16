package example.repo;

import example.model.Customer311;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer311Repository extends CrudRepository<Customer311, Long> {

	List<Customer311> findByLastName(String lastName);
}
