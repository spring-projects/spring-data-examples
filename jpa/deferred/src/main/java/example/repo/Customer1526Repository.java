package example.repo;

import example.model.Customer1526;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1526Repository extends CrudRepository<Customer1526, Long> {

	List<Customer1526> findByLastName(String lastName);
}
