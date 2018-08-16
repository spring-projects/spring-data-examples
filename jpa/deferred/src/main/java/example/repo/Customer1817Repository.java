package example.repo;

import example.model.Customer1817;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1817Repository extends CrudRepository<Customer1817, Long> {

	List<Customer1817> findByLastName(String lastName);
}
