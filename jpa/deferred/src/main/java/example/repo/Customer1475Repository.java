package example.repo;

import example.model.Customer1475;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1475Repository extends CrudRepository<Customer1475, Long> {

	List<Customer1475> findByLastName(String lastName);
}
