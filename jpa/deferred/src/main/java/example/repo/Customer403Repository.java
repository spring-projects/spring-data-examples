package example.repo;

import example.model.Customer403;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer403Repository extends CrudRepository<Customer403, Long> {

	List<Customer403> findByLastName(String lastName);
}
