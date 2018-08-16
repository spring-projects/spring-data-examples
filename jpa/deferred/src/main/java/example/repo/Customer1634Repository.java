package example.repo;

import example.model.Customer1634;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1634Repository extends CrudRepository<Customer1634, Long> {

	List<Customer1634> findByLastName(String lastName);
}
