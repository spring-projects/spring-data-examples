package example.repo;

import example.model.Customer1007;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1007Repository extends CrudRepository<Customer1007, Long> {

	List<Customer1007> findByLastName(String lastName);
}
