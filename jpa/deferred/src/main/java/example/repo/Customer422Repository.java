package example.repo;

import example.model.Customer422;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer422Repository extends CrudRepository<Customer422, Long> {

	List<Customer422> findByLastName(String lastName);
}
