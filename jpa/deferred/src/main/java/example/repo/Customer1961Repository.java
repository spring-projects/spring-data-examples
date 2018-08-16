package example.repo;

import example.model.Customer1961;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1961Repository extends CrudRepository<Customer1961, Long> {

	List<Customer1961> findByLastName(String lastName);
}
