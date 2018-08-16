package example.repo;

import example.model.Customer1389;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1389Repository extends CrudRepository<Customer1389, Long> {

	List<Customer1389> findByLastName(String lastName);
}
