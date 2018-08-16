package example.repo;

import example.model.Customer1187;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1187Repository extends CrudRepository<Customer1187, Long> {

	List<Customer1187> findByLastName(String lastName);
}
