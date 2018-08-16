package example.repo;

import example.model.Customer1033;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1033Repository extends CrudRepository<Customer1033, Long> {

	List<Customer1033> findByLastName(String lastName);
}
