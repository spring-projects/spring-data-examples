package example.repo;

import example.model.Customer1441;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1441Repository extends CrudRepository<Customer1441, Long> {

	List<Customer1441> findByLastName(String lastName);
}
