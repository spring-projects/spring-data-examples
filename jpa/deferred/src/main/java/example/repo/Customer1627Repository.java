package example.repo;

import example.model.Customer1627;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1627Repository extends CrudRepository<Customer1627, Long> {

	List<Customer1627> findByLastName(String lastName);
}
