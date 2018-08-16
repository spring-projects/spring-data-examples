package example.repo;

import example.model.Customer1965;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1965Repository extends CrudRepository<Customer1965, Long> {

	List<Customer1965> findByLastName(String lastName);
}
