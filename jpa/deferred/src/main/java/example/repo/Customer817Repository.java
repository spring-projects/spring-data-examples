package example.repo;

import example.model.Customer817;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer817Repository extends CrudRepository<Customer817, Long> {

	List<Customer817> findByLastName(String lastName);
}
