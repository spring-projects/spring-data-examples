package example.repo;

import example.model.Customer1235;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1235Repository extends CrudRepository<Customer1235, Long> {

	List<Customer1235> findByLastName(String lastName);
}
