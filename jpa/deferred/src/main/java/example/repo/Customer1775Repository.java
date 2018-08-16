package example.repo;

import example.model.Customer1775;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1775Repository extends CrudRepository<Customer1775, Long> {

	List<Customer1775> findByLastName(String lastName);
}
