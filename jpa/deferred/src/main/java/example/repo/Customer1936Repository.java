package example.repo;

import example.model.Customer1936;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1936Repository extends CrudRepository<Customer1936, Long> {

	List<Customer1936> findByLastName(String lastName);
}
