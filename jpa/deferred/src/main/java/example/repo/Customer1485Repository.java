package example.repo;

import example.model.Customer1485;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1485Repository extends CrudRepository<Customer1485, Long> {

	List<Customer1485> findByLastName(String lastName);
}
