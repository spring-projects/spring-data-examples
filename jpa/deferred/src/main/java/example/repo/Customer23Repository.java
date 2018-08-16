package example.repo;

import example.model.Customer23;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer23Repository extends CrudRepository<Customer23, Long> {

	List<Customer23> findByLastName(String lastName);
}
