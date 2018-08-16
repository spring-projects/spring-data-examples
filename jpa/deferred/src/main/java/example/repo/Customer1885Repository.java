package example.repo;

import example.model.Customer1885;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1885Repository extends CrudRepository<Customer1885, Long> {

	List<Customer1885> findByLastName(String lastName);
}
