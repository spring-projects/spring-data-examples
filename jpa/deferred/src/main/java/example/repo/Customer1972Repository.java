package example.repo;

import example.model.Customer1972;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1972Repository extends CrudRepository<Customer1972, Long> {

	List<Customer1972> findByLastName(String lastName);
}
