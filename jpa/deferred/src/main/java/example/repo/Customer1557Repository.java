package example.repo;

import example.model.Customer1557;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1557Repository extends CrudRepository<Customer1557, Long> {

	List<Customer1557> findByLastName(String lastName);
}
