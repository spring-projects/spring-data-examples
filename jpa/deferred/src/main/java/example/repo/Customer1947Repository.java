package example.repo;

import example.model.Customer1947;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1947Repository extends CrudRepository<Customer1947, Long> {

	List<Customer1947> findByLastName(String lastName);
}
