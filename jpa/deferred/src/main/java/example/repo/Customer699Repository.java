package example.repo;

import example.model.Customer699;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer699Repository extends CrudRepository<Customer699, Long> {

	List<Customer699> findByLastName(String lastName);
}
