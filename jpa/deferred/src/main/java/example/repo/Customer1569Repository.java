package example.repo;

import example.model.Customer1569;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1569Repository extends CrudRepository<Customer1569, Long> {

	List<Customer1569> findByLastName(String lastName);
}
