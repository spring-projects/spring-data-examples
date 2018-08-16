package example.repo;

import example.model.Customer1926;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1926Repository extends CrudRepository<Customer1926, Long> {

	List<Customer1926> findByLastName(String lastName);
}
