package example.repo;

import example.model.Customer1888;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1888Repository extends CrudRepository<Customer1888, Long> {

	List<Customer1888> findByLastName(String lastName);
}
