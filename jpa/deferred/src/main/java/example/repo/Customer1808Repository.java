package example.repo;

import example.model.Customer1808;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1808Repository extends CrudRepository<Customer1808, Long> {

	List<Customer1808> findByLastName(String lastName);
}
