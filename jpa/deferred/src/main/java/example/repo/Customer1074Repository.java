package example.repo;

import example.model.Customer1074;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1074Repository extends CrudRepository<Customer1074, Long> {

	List<Customer1074> findByLastName(String lastName);
}
