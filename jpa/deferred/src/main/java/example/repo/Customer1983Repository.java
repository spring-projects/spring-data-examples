package example.repo;

import example.model.Customer1983;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1983Repository extends CrudRepository<Customer1983, Long> {

	List<Customer1983> findByLastName(String lastName);
}
