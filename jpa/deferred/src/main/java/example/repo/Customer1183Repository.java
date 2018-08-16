package example.repo;

import example.model.Customer1183;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1183Repository extends CrudRepository<Customer1183, Long> {

	List<Customer1183> findByLastName(String lastName);
}
