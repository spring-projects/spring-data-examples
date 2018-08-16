package example.repo;

import example.model.Customer1709;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1709Repository extends CrudRepository<Customer1709, Long> {

	List<Customer1709> findByLastName(String lastName);
}
