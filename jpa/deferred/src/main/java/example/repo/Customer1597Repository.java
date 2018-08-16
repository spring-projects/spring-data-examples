package example.repo;

import example.model.Customer1597;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1597Repository extends CrudRepository<Customer1597, Long> {

	List<Customer1597> findByLastName(String lastName);
}
