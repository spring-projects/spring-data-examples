package example.repo;

import example.model.Customer1596;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1596Repository extends CrudRepository<Customer1596, Long> {

	List<Customer1596> findByLastName(String lastName);
}
