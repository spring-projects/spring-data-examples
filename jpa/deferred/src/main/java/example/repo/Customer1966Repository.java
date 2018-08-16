package example.repo;

import example.model.Customer1966;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1966Repository extends CrudRepository<Customer1966, Long> {

	List<Customer1966> findByLastName(String lastName);
}
