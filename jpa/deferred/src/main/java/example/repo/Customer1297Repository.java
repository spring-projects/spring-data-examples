package example.repo;

import example.model.Customer1297;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1297Repository extends CrudRepository<Customer1297, Long> {

	List<Customer1297> findByLastName(String lastName);
}
