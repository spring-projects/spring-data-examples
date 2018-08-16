package example.repo;

import example.model.Customer1786;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1786Repository extends CrudRepository<Customer1786, Long> {

	List<Customer1786> findByLastName(String lastName);
}
