package example.repo;

import example.model.Customer1910;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1910Repository extends CrudRepository<Customer1910, Long> {

	List<Customer1910> findByLastName(String lastName);
}
